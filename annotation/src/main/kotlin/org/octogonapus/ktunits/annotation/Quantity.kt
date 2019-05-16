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

import kotlin.math.pow

open class Quantity(
    val currentDim: Double,
    val tempDim: Double,
    val timeDim: Double,
    val lengthDim: Double,
    val massDim: Double,
    val luminDim: Double,
    val moleDim: Double,
    val angleDim: Double,
    open val value: Double
) {

    constructor(
        currentDim: Number,
        tempDim: Number,
        timeDim: Number,
        lengthDim: Number,
        massDim: Number,
        luminDim: Number,
        moleDim: Number,
        angleDim: Number,
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

    fun dimensionsEqual(other: Quantity) =
        massDim == other.massDim && lengthDim == other.lengthDim && timeDim == other.timeDim &&
            angleDim == other.angleDim

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Quantity) return false

        if (massDim != other.massDim) return false
        if (lengthDim != other.lengthDim) return false
        if (timeDim != other.timeDim) return false
        if (angleDim != other.angleDim) return false
        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        var result = massDim.hashCode()
        result = 31 * result + lengthDim.hashCode()
        result = 31 * result + timeDim.hashCode()
        result = 31 * result + angleDim.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    override fun toString(): String {
        return "Quantity(massDim=$massDim, lengthDim=$lengthDim, timeDim=$timeDim, " +
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

fun Quantity.pow(exp: Number) = pow(exp.toDouble())

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

fun Quantity.ceil() = makeCopy(kotlin.math.ceil(value))

fun Quantity.floor() = makeCopy(kotlin.math.floor(value))

fun Quantity.truncate() = makeCopy(kotlin.math.truncate(value))

fun Quantity.round() = makeCopy(kotlin.math.round(value))

fun Quantity.abs() = makeCopy(kotlin.math.abs(value))

@Suppress("NOTHING_TO_INLINE")
inline fun Quantity.sign() = kotlin.math.sign(value)

fun Quantity.cutRange(min: Double, max: Double): Quantity {
    val middle = max - (max - min) / 2

    return makeCopy(
        if (value > min && value < middle) min
        else if (value in middle..max) max
        else value
    )
}

fun Quantity.map(
    oldMin: Double,
    oldMax: Double,
    newMin: Double,
    newMax: Double
): Quantity = makeCopy((value - oldMin) * ((newMax - newMin) / (oldMax - oldMin)) + newMin)
