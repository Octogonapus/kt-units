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
    private val currentDim: Double,
    private val tempDim: Double,
    private val timeDim: Double,
    private val lengthDim: Double,
    private val massDim: Double,
    private val luminDim: Double,
    private val moleDim: Double,
    private val angleDim: Double
) {

    operator fun plus(other: DimensionData) =
        DimensionData(
            currentDim = currentDim + other.currentDim,
            tempDim = tempDim + other.tempDim,
            timeDim = timeDim + other.timeDim,
            lengthDim = lengthDim + other.lengthDim,
            massDim = massDim + other.massDim,
            luminDim = luminDim + other.luminDim,
            moleDim = moleDim + other.moleDim,
            angleDim = angleDim + other.angleDim
        )

    operator fun minus(other: DimensionData) =
        DimensionData(
            currentDim = currentDim - other.currentDim,
            tempDim = tempDim - other.tempDim,
            timeDim = timeDim - other.timeDim,
            lengthDim = lengthDim - other.lengthDim,
            massDim = massDim - other.massDim,
            luminDim = luminDim - other.luminDim,
            moleDim = moleDim - other.moleDim,
            angleDim = angleDim - other.angleDim
        )

    operator fun times(factor: Double) =
        DimensionData(
            currentDim = currentDim * factor,
            tempDim = tempDim * factor,
            timeDim = timeDim * factor,
            lengthDim = lengthDim * factor,
            massDim = massDim * factor,
            luminDim = luminDim * factor,
            moleDim = moleDim * factor,
            angleDim = angleDim * factor
        )

    operator fun div(denom: Double) =
        DimensionData(
            currentDim = currentDim / denom,
            tempDim = tempDim / denom,
            timeDim = timeDim / denom,
            lengthDim = lengthDim / denom,
            massDim = massDim / denom,
            luminDim = luminDim / denom,
            moleDim = moleDim / denom,
            angleDim = angleDim / denom
        )
}

internal fun Element.getDimensionData() =
    getAnnotation(QuantityType::class.java).let {
        DimensionData(
            currentDim = it.currentDim,
            tempDim = it.tempDim,
            timeDim = it.timeDim,
            lengthDim = it.lengthDim,
            massDim = it.massDim,
            luminDim = it.luminDim,
            moleDim = it.moleDim,
            angleDim = it.angleDim
        )
    }
