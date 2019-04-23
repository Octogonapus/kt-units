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
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import org.octogonapus.ktunits.annotation.QuantityType
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

/**
 * Generates plus, minus, times, and div operators for all annotated quantities.
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(QuantityTypeProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class QuantityTypeProcessor : AbstractProcessor() {

    private fun validateAnnotatedClasses(classes: Set<Element>) {
        classes.forEach {
            if (it.kind != ElementKind.CLASS) {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "Can only be applied to classes, element: $it"
                )
                throw UnsupportedOperationException("Can only be applied to classes, element: $it")
            }

            val generatedSourcesRoot: String =
                processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()
            if (generatedSourcesRoot.isEmpty()) {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "Can't find the target directory for generated Kotlin files."
                )
                throw UnsupportedOperationException("Can't find the target directory for generated Kotlin files.")
            }
        }
    }

    private data class DimensionData(
        val massDim: Long,
        val lengthDim: Long,
        val timeDim: Long,
        val angleDim: Long
    ) {

        operator fun plus(other: DimensionData) =
            DimensionData(
                massDim + other.massDim,
                lengthDim + other.lengthDim,
                timeDim + other.timeDim,
                angleDim + other.angleDim
            )

        operator fun minus(other: DimensionData) =
            DimensionData(
                massDim - other.massDim,
                lengthDim - other.lengthDim,
                timeDim - other.timeDim,
                angleDim - other.angleDim
            )
    }

    private fun Element.getDimensionData() =
        getAnnotation(QuantityType::class.java).let {
            DimensionData(
                it.massDim,
                it.lengthDim,
                it.timeDim,
                it.angleDim
            )
        }

    private data class ElementWithDimensions(
        val element: Element,
        val dimensions: DimensionData
    )

    private fun ElementWithDimensions.isMultiplyCompatible(
        other: ElementWithDimensions,
        possibleReturnTypes: List<ElementWithDimensions>
    ) = possibleReturnTypes.filter { dimensions + other.dimensions == it.dimensions }.toSet()

    private fun ElementWithDimensions.isDivideCompatible(
        other: ElementWithDimensions,
        possibleReturnTypes: List<ElementWithDimensions>
    ) = possibleReturnTypes.filter { dimensions - other.dimensions == it.dimensions }.toSet()

    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        val annotatedClasses = roundEnv.getElementsAnnotatedWith(QuantityType::class.java)
        if (annotatedClasses.isEmpty()) return false
        validateAnnotatedClasses(annotatedClasses)

        val generatedSourcesRoot: String =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()

        val annotatedClassesToDimensions = annotatedClasses.map {
            ElementWithDimensions(it, it.getDimensionData())
        }

        val allFunBuilders = mutableListOf<FunSpec.Builder>()

        val cartesianProduct = ListK.applicative()
            .tupled(annotatedClassesToDimensions.k(), annotatedClassesToDimensions.k())
            .fix()

        cartesianProduct.forEach {
            val returnTypes = it.a.isMultiplyCompatible(it.b, annotatedClassesToDimensions)
            if (returnTypes.size == 1) {
                allFunBuilders.add(
                    buildMultiplyOrDivideFun(
                        it.a.element.asType().asTypeName(),
                        it.b.element.asType().asTypeName(),
                        returnTypes.first().element.asType().asTypeName(),
                        "times",
                        '*'
                    )
                )
            }
        }

        cartesianProduct.forEach {
            val returnTypes = it.a.isDivideCompatible(it.b, annotatedClassesToDimensions)
            if (returnTypes.size == 1) {
                allFunBuilders.add(
                    buildMultiplyOrDivideFun(
                        it.a.element.asType().asTypeName(),
                        it.b.element.asType().asTypeName(),
                        returnTypes.first().element.asType().asTypeName(),
                        "div",
                        '/'
                    )
                )
            }
        }

        annotatedClasses.forEach { element ->
            element as TypeElement
            val ourType = element.asType().asTypeName()
            allFunBuilders.add(buildPlusOrMinusFun(ourType, "plus", '+'))
            allFunBuilders.add(buildPlusOrMinusFun(ourType, "minus", '-'))
        }

        val file = File(generatedSourcesRoot)
        file.mkdir()

        val fileSpecBuilder = FileSpec.builder(
            processingEnv.elementUtils.getPackageOf(annotatedClasses.first()).toString(),
            "BindFieldsGenerated"
        )

        allFunBuilders.forEach { fileSpecBuilder.addFunction(it.build()) }

        fileSpecBuilder.build().writeTo(file)
        return false
    }

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

    override fun getSupportedAnnotationTypes() = setOf(QuantityType::class.java.canonicalName)

    private fun getAnnotationMirror(typeElement: TypeElement, clazz: Class<*>): AnnotationMirror? {
        val clazzName = clazz.name
        for (m in typeElement.annotationMirrors) {
            if (m.annotationType.toString() == clazzName) {
                return m
            }
        }
        return null
    }

    private fun getAnnotationValue(
        annotationMirror: AnnotationMirror,
        key: String
    ): AnnotationValue? {
        for ((key1, value) in annotationMirror.elementValues) {
            if (key1.simpleName.toString() == key) {
                return value
            }
        }
        return null
    }

    @Suppress("unused")
    @SuppressWarnings("UnusedPrivateMember")
    private fun getFriends(foo: TypeElement): List<TypeMirror>? {
        val am = getAnnotationMirror(foo, QuantityType::class.java) ?: return null
        val av = getAnnotationValue(am, "friends")
        return if (av == null) {
            null
        } else {
            @Suppress("UNCHECKED_CAST")
            av.value as List<TypeMirror>
        }
    }

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
