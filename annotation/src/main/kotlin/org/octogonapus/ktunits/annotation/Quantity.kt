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
package org.octogonapus.ktunits.annotation

open class Quantity(
    val massDim: Long,
    val lengthDim: Long,
    val timeDim: Long,
    val angleDim: Long,
    open var value: Double
) {

    fun dimensionsEqual(other: Quantity) =
        massDim == other.massDim && lengthDim == other.lengthDim && timeDim == other.timeDim &&
            angleDim == other.angleDim

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
        Quantity(massDim, lengthDim, timeDim, angleDim, value + other.value)
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
        Quantity(massDim, lengthDim, timeDim, angleDim, value - other.value)
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
operator fun Quantity.times(other: Quantity) =
    Quantity(
        massDim + other.massDim,
        lengthDim + other.lengthDim,
        timeDim + other.timeDim,
        angleDim + other.angleDim,
        value * other.value
    )

/**
 * Returns the quotient of [this] and [other].
 *
 * This is an extension function so that it can be overloaded with generated extension functions.
 */
operator fun Quantity.div(other: Quantity) =
    Quantity(
        massDim - other.massDim,
        lengthDim - other.lengthDim,
        timeDim - other.timeDim,
        angleDim - other.angleDim,
        value / other.value
    )
