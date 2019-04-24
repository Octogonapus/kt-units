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

import org.octogonapus.ktunits.annotation.QuantityType
import javax.lang.model.element.Element

internal data class DimensionData(
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

internal fun Element.getDimensionData() =
    getAnnotation(QuantityType::class.java).let {
        DimensionData(
            it.massDim,
            it.lengthDim,
            it.timeDim,
            it.angleDim
        )
    }
