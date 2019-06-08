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
import org.octogonapus.ktunits.annotation.Quantity
import org.octogonapus.ktunits.annotation.QuantityBlacklist
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
@SuppressWarnings("TooManyFunctions", "StringLiteralDuplication")
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(QuantityTypeProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class QuantityTypeProcessor : AbstractProcessor() {

    override fun getSupportedAnnotationTypes() = setOf(QuantityType::class.java.canonicalName)

    @SuppressWarnings("ComplexMethod", "NestedBlockDepth")
    override fun process(
        annotations: MutableSet<out TypeElement>,
        roundEnv: RoundEnvironment
    ): Boolean {
        fun TypeMirror.asTypeElement(): TypeElement =
            processingEnv.typeUtils.asElement(this) as TypeElement

        val annotatedTypeClasses = roundEnv.getElementsAnnotatedWith(QuantityType::class.java)
        if (annotatedTypeClasses.isEmpty()) return false
        validateAnnotatedClasses(annotatedTypeClasses)

        val generatedSourcesRoot: String =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME].orEmpty()

        val annotatedClassesToDimensions = annotatedTypeClasses.map {
            ElementWithDimensions(it, it.getDimensionData())
        }

        val allFunctions = mutableListOf<FunSpec>()
        val allProperties = mutableListOf<PropertySpec>()

        // All the return types we can't generate functions with for a given receiver type
        val blacklists = validateAnnotatedClasses(
            roundEnv.getElementsAnnotatedWith(QuantityBlacklist::class.java)
        ).map {
            val blacklistedClasses = getBlacklistedClasses(it.asType().asTypeElement())
            it.asType().asTypeName() to blacklistedClasses
        }.toMap()

        val cartesianProduct = ListK.applicative()
            .tupled(annotatedClassesToDimensions.k(), annotatedClassesToDimensions.k())
            .fix()

        cartesianProduct.forEach {
            // Emitting multiple functions here will cause conflicting declarations, but it helps
            // the user know the root cause
            annotatedClassesToDimensions.forEach { returnType ->
                val blacklistedClasses = blacklists[it.a.typeName] ?: emptyList()
                val blacklisted = blacklistedClasses.contains(returnType.element.asType())

                if (it.a.element.simpleName.contentEquals("Unitless")) {
                    // unitless * x = x
                    // Only emit something if the parameter and return type are equal (otherwise
                    // the overload will be conflicting).
                    if (it.b == returnType) {
                        allFunctions += generateMultAndDivMethods(
                            blacklisted,
                            it.a,
                            it.b,
                            returnType
                        )
                    }
                } else if (it.b.element.simpleName.contentEquals("Unitless")) {
                    // x * unitless = x
                    // Only emit something if the receiver and return type are equal (otherwise
                    // the overload will be conflicting).
                    if (it.a == returnType) {
                        allFunctions += generateMultAndDivMethods(
                            blacklisted,
                            it.a,
                            it.b,
                            returnType
                        )
                    }
                } else {
                    // Otherwise there are no conflicts
                    allFunctions += generateMultAndDivMethods(blacklisted, it.a, it.b, returnType)
                }
            }

            if (it.a.isSqrtCompatible(it.b)) {
                allFunctions.add(buildSqrtFun(it.a.typeName, it.b.typeName))
            }

            if (it.a.isPowCompatible(it.b, 2.0)) {
                allFunctions.add(buildSquaredFun(it.a.typeName, it.b.typeName))
            }

            if (it.a.isPowCompatible(it.b, 3.0)) {
                allFunctions.add(buildCubedFun(it.a.typeName, it.b.typeName))
            }

            if (it.a.isInverseCompatible(it.b)) {
                allFunctions.add(buildNumberDivideQuantityFun(it.a.typeName, it.b.typeName))
            }
        }

        annotatedTypeClasses.forEach { element ->
            val typeName = element.asType().asTypeName()
            allFunctions.add(buildPlusFun(typeName))
            allFunctions.add(buildMinusFun(typeName))
            allFunctions.add(buildCeilFun(typeName))
            allFunctions.add(buildFloorFun(typeName))
            allFunctions.add(buildTruncateFun(typeName))
            allFunctions.add(buildRoundFun(typeName))
            allFunctions.add(buildAbsFun(typeName))
            allFunctions.add(buildCutRangeFun(typeName))
            allFunctions.add(buildMapFun(typeName))
            allFunctions.add(buildFromFun(ElementWithDimensions(element)))
            allFunctions.addAll(buildNumberMultiplyFuns(typeName))
            allFunctions.add(buildQuantityDivideNumberFun(typeName))
            allFunctions.add(buildCompareToFun(typeName))

            val conversions = element.getAnnotation(QuantityConversions::class.java)
            val conversionFuns = conversions?.values?.flatMap {
                buildConversionFuns(element.asType().asTypeName(), it)
            }

            conversionFuns?.let { allProperties.addAll(it) }
        }

        val file = File(generatedSourcesRoot)
        file.mkdir()

        val fileSpecBuilder = FileSpec.builder(
            processingEnv.elementUtils.getPackageOf(annotatedTypeClasses.first()).toString(),
            "GeneratedQuantities"
        )

        allFunctions.forEach { fileSpecBuilder.addFunction(it) }
        allProperties.forEach { fileSpecBuilder.addProperty(it) }

        fileSpecBuilder.build().writeTo(file)

        return false
    }

    /**
     * Generates multiply and divide operator overloads (and handles blacklisted return types).
     *
     * @param blacklisted Whether the return type is blacklisted.
     * @param receiver The receiver type.
     * @param parameter The parameter type.
     * @param returnType The return type.
     */
    private fun generateMultAndDivMethods(
        blacklisted: Boolean,
        receiver: ElementWithDimensions,
        parameter: ElementWithDimensions,
        returnType: ElementWithDimensions
    ): List<FunSpec> {
        val functions = mutableListOf<FunSpec>()

        // Generate operator functions for non-blacklisted return types
        if (!blacklisted) {
            if (receiver.isMultiplyCompatible(parameter, returnType)) {
                functions.add(buildMultiplyFun(receiver, parameter, returnType))
            }

            if (receiver.isDivideCompatible(parameter, returnType)) {
                functions.add(buildDivideFun(receiver, parameter, returnType))
            }
        }

        // Generate non-operator functions for blacklisted return types
        if (blacklisted) {
            if (receiver.isMultiplyCompatible(parameter, returnType)) {
                functions.add(buildBlacklistedMultiplyFun(receiver, parameter, returnType))
            }

            if (receiver.isDivideCompatible(parameter, returnType)) {
                functions.add(buildBlacklistedDivideFun(receiver, parameter, returnType))
            }
        }

        return functions
    }

    private fun validateAnnotatedClasses(classes: Set<Element>): Set<Element> {
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

        return classes
    }

    private fun buildMultiplyFun(
        receiverType: ElementWithDimensions,
        parameterType: ElementWithDimensions,
        returnType: ElementWithDimensions
    ) = buildMultiplyOrDivideFun(
        receiverType.typeName,
        parameterType.typeName,
        returnType.typeName,
        "times",
        '*'
    )

    private fun buildBlacklistedMultiplyFun(
        receiverType: ElementWithDimensions,
        parameterType: ElementWithDimensions,
        returnType: ElementWithDimensions
    ) = buildMultiplyOrDivideFun(
        receiverType.typeName,
        parameterType.typeName,
        returnType.typeName,
        "times${returnType.element.simpleName}",
        '*',
        false
    )

    private fun buildDivideFun(
        receiverType: ElementWithDimensions,
        parameterType: ElementWithDimensions,
        returnType: ElementWithDimensions
    ) = buildMultiplyOrDivideFun(
        receiverType.typeName,
        parameterType.typeName,
        returnType.typeName,
        "div",
        '/'
    )

    private fun buildBlacklistedDivideFun(
        receiverType: ElementWithDimensions,
        parameterType: ElementWithDimensions,
        returnType: ElementWithDimensions
    ) = buildMultiplyOrDivideFun(
        receiverType.typeName,
        parameterType.typeName,
        returnType.typeName,
        "div${returnType.element.simpleName}",
        '/',
        false
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

    @SuppressWarnings("LongParameterList")
    private fun buildMultiplyOrDivideFun(
        receiverType: TypeName,
        parameterType: TypeName,
        returnType: TypeName,
        name: String,
        op: Char,
        isOperator: Boolean = true
    ) = FunSpec.builder(name)
        .addModifiers(KModifier.PUBLIC)
        .apply { if (isOperator) addModifiers(KModifier.OPERATOR) }
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

    private fun buildSqrtFun(
        receiverType: TypeName,
        returnType: TypeName
    ) = FunSpec.builder("sqrt")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(returnType)
        .addStatement(
            """
            |return %T(kotlin.math.sqrt(value))
            """.trimMargin(),
            returnType
        )
        .build()

    private fun buildSquaredFun(
        receiverType: TypeName,
        returnType: TypeName
    ) = FunSpec.builder("squared")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(returnType)
        .addStatement(
            """
            |return %T(Math.pow(value, 2.0))
            """.trimMargin(),
            returnType
        )
        .build()

    private fun buildCubedFun(
        receiverType: TypeName,
        returnType: TypeName
    ) = FunSpec.builder("cubed")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(returnType)
        .addStatement(
            """
            |return %T(Math.pow(value, 3.0))
            """.trimMargin(),
            returnType
        )
        .build()

    private fun buildCeilFun(
        receiverType: TypeName
    ) = FunSpec.builder("ceil")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(receiverType)
        .addStatement(
            """
            |return %T(kotlin.math.ceil(value))
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun buildFloorFun(
        receiverType: TypeName
    ) = FunSpec.builder("floor")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(receiverType)
        .addStatement(
            """
            |return %T(kotlin.math.floor(value))
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun buildTruncateFun(
        receiverType: TypeName
    ) = FunSpec.builder("truncate")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(receiverType)
        .addStatement(
            """
            |return %T(kotlin.math.truncate(value))
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun buildRoundFun(
        receiverType: TypeName
    ) = FunSpec.builder("round")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(receiverType)
        .addStatement(
            """
            |return %T(kotlin.math.round(value))
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun buildAbsFun(
        receiverType: TypeName
    ) = FunSpec.builder("abs")
        .addModifiers(KModifier.PUBLIC, KModifier.INLINE)
        .receiver(receiverType)
        .returns(receiverType)
        .addStatement(
            """
            |return %T(kotlin.math.abs(value))
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun buildCutRangeFun(
        receiverType: TypeName
    ) = FunSpec.builder("cutRange")
        .addModifiers(KModifier.PUBLIC)
        .receiver(receiverType)
        .returns(receiverType)
        .addParameter("min", Double::class)
        .addParameter("max", Double::class)
        .addStatement(
            """
            |val middle = max - ((max - min) / 2)
            |
            |return %T(
            |    if (value > min && value < middle) min
            |    else if (value in middle..max) max else value
            |)
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun buildMapFun(
        receiverType: TypeName
    ) = FunSpec.builder("map")
        .addModifiers(KModifier.PUBLIC)
        .receiver(receiverType)
        .returns(receiverType)
        .addParameter("oldMin", Double::class)
        .addParameter("oldMax", Double::class)
        .addParameter("newMin", Double::class)
        .addParameter("newMax", Double::class)
        .addStatement(
            """
            |return %T((value - oldMin) * ((newMax - newMin) / (oldMax - oldMin)) + newMin)
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun Element.typeName() = asType().asTypeName()

    private fun Element.enclosedCompanion(): Element {
        val companion = enclosedElements.firstOrNull { it.simpleName.contentEquals("Companion") }
        return if (companion == null) {
            val message = "Class ${typeName()} needs a companion object declared, " +
                "but does not have one."

            printError(message)
            throw IllegalStateException(message)
        } else {
            companion
        }
    }

    private fun buildFromFun(
        receiverElement: ElementWithDimensions
    ) = FunSpec.builder("from")
        .addModifiers(KModifier.PUBLIC)
        .receiver(receiverElement.element.enclosedCompanion().typeName())
        .returns(receiverElement.typeName)
        .addParameter("other", Quantity::class)
        .addStatement(
            """
            if (other.dimensionsEqual(
                    currentDim = ${receiverElement.dimensions.currentDim},
                    tempDim = ${receiverElement.dimensions.tempDim},
                    timeDim = ${receiverElement.dimensions.timeDim},
                    lengthDim = ${receiverElement.dimensions.lengthDim},
                    massDim = ${receiverElement.dimensions.massDim},
                    luminDim = ${receiverElement.dimensions.luminDim},
                    moleDim = ${receiverElement.dimensions.moleDim},
                    angleDim = ${receiverElement.dimensions.angleDim}
                )
            ) {
                return %T(other.value)
            } else {
                throw IllegalArgumentException(
                    ""${'"'}
                    |Cannot convert quantity to %T:
                    |${'$'}other
                    ""${'"'}.trimMargin()
                )
            }
            """.trimIndent(),
            receiverElement.typeName,
            receiverElement.typeName
        )
        .build()

    private fun buildNumberMultiplyFuns(
        receiverType: TypeName
    ) = listOf(
        FunSpec.builder("times")
            .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR)
            .receiver(receiverType)
            .returns(receiverType)
            .addParameter("other", Number::class)
            .addStatement(
                """
                |return %T(value * other.toDouble())
                """.trimMargin(),
                receiverType
            )
            .build(),
        FunSpec.builder("times")
            .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR)
            .receiver(Number::class)
            .returns(receiverType)
            .addParameter("other", receiverType)
            .addStatement(
                """
                |return %T(other.value * this.toDouble())
                """.trimMargin(),
                receiverType
            )
            .build()
    )

    private fun buildQuantityDivideNumberFun(
        receiverType: TypeName
    ) = FunSpec.builder("div")
        .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR)
        .receiver(receiverType)
        .returns(receiverType)
        .addParameter("other", Number::class)
        .addStatement(
            """
            |return %T(value / other.toDouble())
            """.trimMargin(),
            receiverType
        )
        .build()

    private fun buildNumberDivideQuantityFun(
        paramType: TypeName,
        returnType: TypeName
    ) = FunSpec.builder("div")
        .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR)
        .receiver(Number::class)
        .returns(returnType)
        .addParameter("other", paramType)
        .addStatement(
            """
            |return %T(toDouble() / other.value)
            """.trimMargin(),
            returnType
        )
        .build()

    private fun buildCompareToFun(
        type: TypeName
    ) = FunSpec.builder("compareTo")
        .addModifiers(KModifier.PUBLIC, KModifier.OPERATOR)
        .receiver(type)
        .returns(Int::class)
        .addParameter("other", type)
        .addStatement(
            """
            |return value.compareTo(other.value)
            """.trimMargin()
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
        PropertySpec.builder(conversionData.name, Double::class.asTypeName())
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
        @Suppress("SameParameterValue") key: String
    ): AnnotationValue? {
        for ((key1, value) in annotationMirror.elementValues) {
            if (key1.simpleName.toString() == key) {
                return value
            }
        }

        return null
    }

    private fun getBlacklistedClasses(foo: TypeElement): List<TypeMirror> {
        val am = getAnnotationMirror(foo, QuantityBlacklist::class.java)!!
        val av = getAnnotationValue(am, "blacklistedClasses")

        @Suppress("UNCHECKED_CAST")
        val values = av?.value as List<AnnotationValue>

        return values.map { it.value as TypeMirror }
    }

    private fun printError(message: String) =
        processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, message)

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}
