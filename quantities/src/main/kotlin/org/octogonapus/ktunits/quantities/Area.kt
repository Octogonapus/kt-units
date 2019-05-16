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
package org.octogonapus.ktunits.quantities

import org.octogonapus.ktunits.annotation.Quantity
import org.octogonapus.ktunits.annotation.QuantityConversion
import org.octogonapus.ktunits.annotation.QuantityConversions
import org.octogonapus.ktunits.annotation.QuantityType

@QuantityType(
    currentDim = 0.0,
    tempDim = 0.0,
    timeDim = 0.0,
    lengthDim = 2.0,
    massDim = 0.0,
    luminDim = 0.0,
    moleDim = 0.0,
    angleDim = 0.0
)
@QuantityConversions(
    QuantityConversion("sqMeter", 1.0),
    QuantityConversion("sqInch", 6.452 * 1e-4),
    QuantityConversion("sqCentimeter", 1e-4),
    QuantityConversion("sqYard", 0.8361),
    QuantityConversion("sqMile", 2.59 * 1e+6)
)
data class Area(
    override val value: Double
) : Quantity(
    currentDim = 0.0,
    tempDim = 0.0,
    timeDim = 0,
    lengthDim = 2,
    massDim = 0,
    luminDim = 0.0,
    moleDim = 0.0,
    angleDim = 0,
    value = value
)
