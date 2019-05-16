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
    lengthDim = 1.0,
    massDim = 0.0,
    luminDim = 0.0,
    moleDim = 0.0,
    angleDim = 0.0
)
@QuantityConversions(
    QuantityConversion("gigameter", 1e+9),
    QuantityConversion("megameter", 1e+6),
    QuantityConversion("kilometer", 1e+3),
    QuantityConversion("hectometer", 1e+2),
    QuantityConversion("decameter", 1e+1),
    QuantityConversion("meter", 1.0),
    QuantityConversion("decimeter", 1e-1),
    QuantityConversion("centimeter", 1e-2),
    QuantityConversion("millimeter", 1e-3),
    QuantityConversion("micrometer", 1e-6),
    QuantityConversion("nanometer", 1e-9),
    QuantityConversion("yard", 0.9144),
    QuantityConversion("inch", 0.0254),
    QuantityConversion("foot", 0.3048),
    QuantityConversion("mile", 1609.344)
)
data class Length(
    override val value: Double
) : Quantity(
    currentDim = 0.0,
    tempDim = 0.0,
    timeDim = 0,
    lengthDim = 1,
    massDim = 0,
    luminDim = 0.0,
    moleDim = 0.0,
    angleDim = 0,
    value = value
)
