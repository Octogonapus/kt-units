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
@file:SuppressWarnings("TooManyFunctions")

package org.octogonapus.ktunits.annotation

import java.lang.Double.doubleToRawLongBits
import kotlin.math.pow

open class Quantity(
    val currentDim: Double = 0.0,
    val tempDim: Double = 0.0,
    val timeDim: Double = 0.0,
    val lengthDim: Double = 0.0,
    val massDim: Double = 0.0,
    val luminDim: Double = 0.0,
    val moleDim: Double = 0.0,
    val angleDim: Double = 0.0,
    open val value: Double
) {

    constructor(
        currentDim: Number = 0,
        tempDim: Number = 0,
        timeDim: Number = 0,
        lengthDim: Number = 0,
        massDim: Number = 0,
        luminDim: Number = 0,
        moleDim: Number = 0,
        angleDim: Number = 0,
        value: Number
    ) : this(
        currentDim = currentDim.toDouble(),
        tempDim = tempDim.toDouble(),
        timeDim = timeDim.toDouble(),
        lengthDim = lengthDim.toDouble(),
        massDim = massDim.toDouble(),
        luminDim = luminDim.toDouble(),
        moleDim = moleDim.toDouble(),
        angleDim = angleDim.toDouble(),
        value = value.toDouble()
    )

    fun dimensionsEqual(other: Quantity) = dimensionsEqual(
        currentDim = other.currentDim,
        tempDim = other.tempDim,
        timeDim = other.timeDim,
        lengthDim = other.lengthDim,
        massDim = other.massDim,
        luminDim = other.luminDim,
        moleDim = other.moleDim,
        angleDim = other.angleDim
    )

    @SuppressWarnings("LongParameterList")
    fun dimensionsEqual(
        currentDim: Double = 0.0,
        tempDim: Double = 0.0,
        timeDim: Double = 0.0,
        lengthDim: Double = 0.0,
        massDim: Double = 0.0,
        luminDim: Double = 0.0,
        moleDim: Double = 0.0,
        angleDim: Double = 0.0
    ) = this.currentDim == currentDim &&
        this.tempDim == tempDim &&
        this.timeDim == timeDim &&
        this.lengthDim == lengthDim &&
        this.massDim == massDim &&
        this.luminDim == luminDim &&
        this.moleDim == moleDim &&
        this.angleDim == angleDim

    fun makeCopy(newValue: Double) = Quantity(
        currentDim = currentDim,
        tempDim = tempDim,
        timeDim = timeDim,
        lengthDim = lengthDim,
        massDim = massDim,
        luminDim = luminDim,
        moleDim = moleDim,
        angleDim = angleDim,
        value = newValue
    )

    @SuppressWarnings("ComplexMethod")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Quantity) return false

        if (currentDim != other.currentDim) return false
        if (tempDim != other.tempDim) return false
        if (timeDim != other.timeDim) return false
        if (lengthDim != other.lengthDim) return false
        if (massDim != other.massDim) return false
        if (luminDim != other.luminDim) return false
        if (moleDim != other.moleDim) return false
        if (angleDim != other.angleDim) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currentDim.hashCode()
        result = 31 * result + tempDim.hashCode()
        result = 31 * result + timeDim.hashCode()
        result = 31 * result + lengthDim.hashCode()
        result = 31 * result + massDim.hashCode()
        result = 31 * result + luminDim.hashCode()
        result = 31 * result + moleDim.hashCode()
        result = 31 * result + angleDim.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "Quantity(currentDim=$currentDim, tempDim=$tempDim, timeDim=$timeDim, " +
            "lengthDim=$lengthDim, massDim=$massDim, luminDim=$luminDim, moleDim=$moleDim, " +
            "angleDim=$angleDim, value=$value)"
    }
}

/**
 * Returns the sum of [this] and [other].
 *
 * This is an extension function so that it can be overloaded with generated extension functions.
 */
operator fun Quantity.plus(other: Quantity) =
    if (dimensionsEqual(other)) {
        makeCopy(value + other.value)
    } else {
        throw UnsupportedOperationException(
            """
            |Cannot add quantities with unequal dimensions:
            |$this
            |$other
            """.trimMargin()
        )
    }

/**
 * Returns the difference of [this] and [other].
 *
 * This is an extension function so that it can be overloaded with generated extension functions.
 */
operator fun Quantity.minus(other: Quantity) =
    if (dimensionsEqual(other)) {
        makeCopy(value - other.value)
    } else {
        throw UnsupportedOperationException(
            """
            |Cannot subtract quantities with unequal dimensions:
            |$this
            |$other
            """.trimMargin()
        )
    }

/**
 * Returns the product of [this] and [other].
 *
 * This is an extension function so that it can be overloaded with generated extension functions.
 */
operator fun Quantity.times(other: Quantity) = Quantity(
    currentDim = currentDim + other.currentDim,
    tempDim = tempDim + other.tempDim,
    timeDim = timeDim + other.timeDim,
    lengthDim = lengthDim + other.lengthDim,
    massDim = massDim + other.massDim,
    luminDim = luminDim + other.luminDim,
    moleDim = moleDim + other.moleDim,
    angleDim = angleDim + other.angleDim,
    value = value * other.value
)

/**
 * Returns the product of [this] and [other].
 *
 * This is an extension function so that it can be overloaded with generated extension functions.
 */
operator fun Quantity.times(other: Number) = makeCopy(value * other.toDouble())

/**
 * Returns the quotient of [this] and [other].
 *
 * This is an extension function so that it can be overloaded with generated extension functions.
 */
operator fun Quantity.div(other: Quantity) = Quantity(
    currentDim = currentDim - other.currentDim,
    tempDim = tempDim - other.tempDim,
    timeDim = timeDim - other.timeDim,
    lengthDim = lengthDim - other.lengthDim,
    massDim = massDim - other.massDim,
    luminDim = luminDim - other.luminDim,
    moleDim = moleDim - other.moleDim,
    angleDim = angleDim - other.angleDim,
    value = value / other.value
)

/**
 * Returns the quotient of [this] and [other].
 *
 * This is an extension function so that it can be overloaded with generated extension functions.
 */
operator fun Quantity.div(other: Number) = makeCopy(value / other.toDouble())

/**
 * Computes the positive square root of this value.
 *
 * @return A new value.
 */
fun Quantity.sqrt() = Quantity(
    currentDim = currentDim / 2,
    tempDim = tempDim / 2,
    timeDim = timeDim / 2,
    lengthDim = lengthDim / 2,
    massDim = massDim / 2,
    luminDim = luminDim / 2,
    moleDim = moleDim / 2,
    angleDim = angleDim / 2,
    value = kotlin.math.sqrt(value)
)

/**
 * Raises this value to the power [exp].
 *
 * @param exp The exponent.
 * @return A new value.
 */
fun Quantity.pow(exp: Double) = Quantity(
    currentDim = currentDim * exp,
    tempDim = tempDim * exp,
    timeDim = timeDim * exp,
    lengthDim = lengthDim * exp,
    massDim = massDim * exp,
    luminDim = luminDim * exp,
    moleDim = moleDim * exp,
    angleDim = angleDim * exp,
    value = value.pow(exp)
)

/**
 * Raises this value to the power [exp].
 *
 * @param exp The exponent.
 * @return A new value.
 */
fun Quantity.pow(exp: Number) = pow(exp.toDouble())

/**
 * Cuts out a range from the number. The new range of the input number will be
 * (-inf, min]U[max, +inf). If value sits equally between min and max, max will be returned.
 *
 * @param min The lower bound of range.
 * @param max The upper bound of range.
 * @return The remapped value.
 */
fun Quantity.cutRange(min: Double, max: Double): Quantity {
    val middle = max - (max - min) / 2

    return makeCopy(
        if (value > min && value < middle) min
        else if (value in middle..max) max
        else value
    )
}

/**
 * Remap a value in the range [oldMin, oldMax] to the range [newMin, newMax].
 *
 * @param oldMin The old range lower bound.
 * @param oldMax The old range upper bound.
 * @param newMin The new range lower bound.
 * @param newMax The new range upper bound.
 * @return The remapped value in the new range [newMin, newMax].
 */
fun Quantity.map(
    oldMin: Double,
    oldMax: Double,
    newMin: Double,
    newMax: Double
): Quantity = makeCopy((value - oldMin) * ((newMax - newMin) / (oldMax - oldMin)) + newMin)

private val negativeZeroDoubleBits = doubleToRawLongBits(-0.0)

/**
 * Returns the smaller of two values. If either value is `NaN`, then the result is `NaN`.
 *
 * @param a The first value to compare.
 * @param b The second value to compare.
 * @return The minimum.
 */
fun <T : Quantity> min(a: T, b: T): T = when {
    // a is NaN
    a.value != a.value -> a

    // Raw conversion ok since NaN can't map to -0.0.
    a.value == 0.0 && b.value == 0.0 && doubleToRawLongBits(b.value) == negativeZeroDoubleBits -> b

    else -> if (a.value <= b.value) a else b
}

/**
 * Returns the greater of two values. If either value is `NaN`, then the result is `NaN`.
 *
 * @param a The first value to compare.
 * @param b The second value to compare.
 * @return The maximum.
 */
fun <T : Quantity> max(a: T, b: T): T = when {
    // a is NaN
    a.value != a.value -> a

    // Raw conversion ok since NaN can't map to -0.0.
    a.value == 0.0 && b.value == 0.0 && doubleToRawLongBits(b.value) == negativeZeroDoubleBits -> b

    else -> if (a.value >= b.value) a else b
}

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.exp() = kotlin.math.exp(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.expm1() = kotlin.math.expm1(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.log(base: Double) = kotlin.math.log(value, base)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.ln() = kotlin.math.ln(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.log10() = kotlin.math.log10(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.log2() = kotlin.math.log2(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.ln1p() = kotlin.math.ln1p(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.sin() = kotlin.math.sin(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.cos() = kotlin.math.cos(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.tan() = kotlin.math.tan(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.asin() = kotlin.math.asin(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.acos() = kotlin.math.acos(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.atan() = kotlin.math.atan(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.sinh() = kotlin.math.sinh(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.cosh() = kotlin.math.cosh(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.tanh() = kotlin.math.tanh(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.asinh() = kotlin.math.asinh(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.acosh() = kotlin.math.acosh(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.atanh() = kotlin.math.atanh(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.sign() = kotlin.math.sign(value)

fun Quantity.ceil() = makeCopy(kotlin.math.ceil(value))

fun Quantity.floor() = makeCopy(kotlin.math.floor(value))

fun Quantity.truncate() = makeCopy(kotlin.math.truncate(value))

fun Quantity.round() = makeCopy(kotlin.math.round(value))

fun Quantity.abs() = makeCopy(kotlin.math.abs(value))
