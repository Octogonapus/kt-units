/*
 * This file is part of kt-units.
 *
 * kt-units is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kt-units is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with kt-units.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.octogonapus.ktunits.processor

import arrow.data.ListK
import arrow.data.extensions.listk.applicative.applicative
import arrow.data.fix
import arrow.data.k
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import org.octogonapus.ktunits.annotation.QuantityConversion
import org.octogonapus.ktunits.annotation.QuantityConversions
import org.octogonapus.ktunits.annotation.QuantityType
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 * Generates plus, minus, times, and div operators for all annotated quantities.
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(QuantityTypeProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class QuantityTypeProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() = setOf(QuantityType::class.java.canonicalName)

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        val annotatedTypeClasses = roundEnv.getElementsAnnotatedWith(QuantityType::class.java)
        if (annotatedTypeClasses.isEmpty()) return false
        validateAnnotatedClasses(annotatedTypeClasses)

        val generatedSourcesRoot: String =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()

        val annotatedClassesToDimensions = annotatedTypeClasses.map {
            ElementWithDimensions(it, it.getDimensionData())
        }

        val allFunBuilders = mutableListOf<FunSpec>()
        val allPropBuilders = mutableListOf<PropertySpec>()

        val cartesianProduct = ListK.applicative()
            .tupled(annotatedClassesToDimensions.k(), annotatedClassesToDimensions.k())
            .fix()

        cartesianProduct.forEach {
            // Emitting multiple functions here will cause conflicting declarations, but it helps
            // the user know the root cause
            it.a.isMultiplyCompatible(it.b, annotatedClassesToDimensions).forEach { returnType ->
                allFunBuilders.add(
                    buildMultiplyFun(it.a, it.b, returnType)
                )
            }
        }

        cartesianProduct.forEach {
            // Emitting multiple functions here will cause conflicting declarations, but it helps
            // the user know the root cause
            it.a.isDivideCompatible(it.b, annotatedClassesToDimensions).forEach { returnType ->
                allFunBuilders.add(
                    buildDivideFun(it.a, it.b, returnType)
                )
            }
        }

        annotatedTypeClasses.forEach { element ->
            val typeName = element.asType().asTypeName()
            allFunBuilders.add(buildPlusFun(typeName))
            allFunBuilders.add(buildMinusFun(typeName))
        }

        annotatedTypeClasses.forEach { element ->
            val conversions = element.getAnnotation(QuantityConversions::class.java)
            val conversionFuns = conversions?.values?.flatMap {
                buildConversionFuns(element.asType().asTypeName(), it)
            }

            conversionFuns?.let { allPropBuilders.addAll(it) }
        }

        val file = File(generatedSourcesRoot)
        file.mkdir()

        val fileSpecBuilder = FileSpec.builder(
            processingEnv.elementUtils.getPackageOf(annotatedTypeClasses.first()).toString(),
            "GeneratedQuantities"
        )

        allFunBuilders.forEach { fileSpecBuilder.addFunction(it) }
        allPropBuilders.forEach { fileSpecBuilder.addProperty(it) }

        fileSpecBuilder.build().writeTo(file)

        return false
    }

    private fun validateAnnotatedClasses(classes: Set<Element>) {
        classes.forEach { element ->
            if (element.kind != ElementKind.CLASS) {
                "Can only be applied to classes, element: $element".let {
                    printError(it)
                    throw UnsupportedOperationException(it)
                }
            }

            val generatedSourcesRoot =
                processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
            if (generatedSourcesRoot.isEmpty()) {
                "Can't find the target directory for generated Kotlin files.".let {
                    printError(it)
                    throw UnsupportedOperationException(it)
                }
            }
        }
    }

    private fun buildMultiplyFun(
        receiverType: ElementWithDimensions,
        parameterType: ElementWithDimensions,
        returnType: ElementWithDimensions
    ) = buildMultiplyOrDivideFun(
        receiverType.element.asType().asTypeName(),
        parameterType.element.asType().asTypeName(),
        returnType.element.asType().asTypeName(),
        "times",
        '*'
    )

    private fun buildDivideFun(
        receiverType: ElementWithDimensions,
        parameterType: ElementWithDimensions,
        returnType: ElementWithDimensions
    ) = buildMultiplyOrDivideFun(
        receiverType.element.asType().asTypeName(),
        parameterType.element.asType().asTypeName(),
        returnType.element.asType().asTypeName(),
        "div",
        '/'
    )

    private fun buildPlusFun(
        type: TypeName
    ) = buildPlusOrMinusFun(type, "plus", '+')

    private fun buildMinusFun(
        type: TypeName
    ) = buildPlusOrMinusFun(type, "minus", '-')

    private fun buildPlusOrMinusFun(type: TypeName, name: String, op: Char) =
        FunSpec.builder(name)
            .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR)
            .receiver(type)
            .addParameter("other", type)
            .returns(type)
            .addStatement(
                "return %T(value $op other.value)",
                type
            )
            .build()

    private fun buildMultiplyOrDivideFun(
        receiverType: TypeName,
        parameterType: TypeName,
        returnType: TypeName,
        name: String,
        op: Char
    ) = FunSpec.builder(name)
        .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR)
        .receiver(receiverType)
        .addParameter("other", parameterType)
        .returns(returnType)
        .addStatement(
            """
            |return %T(value $op other.value)
            """.trimMargin(),
            returnType
        )
        .build()

    private fun buildConversionFuns(
        conversionClass: TypeName,
        conversionData: QuantityConversion
    ) = listOf(
        PropertySpec.builder(conversionData.name, conversionClass)
            .receiver(Number::class.asTypeName())
            .getter(
                FunSpec.builder("get()")
                    .addModifiers(KModifier.PUBLIC)
                    .receiver(Number::class.asTypeName())
                    .addStatement(
                        """
                        |return %T(toDouble() * ${conversionData.ratio})
                        """.trimMargin(),
                        conversionClass
                    ).build()
            )
            .build(),
        PropertySpec.builder(conversionData.name, Number::class.asTypeName())
            .receiver(conversionClass)
            .getter(
                FunSpec.builder("get()")
                    .addModifiers(KModifier.PUBLIC)
                    .receiver(conversionClass)
                    .addStatement(
                        """
                        |return value / ${conversionData.ratio}
                        """.trimMargin()
                    ).build()
            )
            .build()
    )

    private fun printError(message: String) =
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message)

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
