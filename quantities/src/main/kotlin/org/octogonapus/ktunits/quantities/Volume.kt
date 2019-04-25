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

@QuantityType(0, 3, 0, 0)
@QuantityConversions(
    QuantityConversion("cubicMeter", 1.0),
    QuantityConversion("cubicCentimeter", 1e-6),
    QuantityConversion("cubicInch", (1.639 * 1e-5)),
    QuantityConversion("cubicFoot", (2.832 * 1e-2)),
    QuantityConversion("cubicYard", (7.646 * 1e-1)),
    QuantityConversion("liter", 1e-3),
    QuantityConversion("quart", (9.464 * 1e-4)),
    QuantityConversion("pint", (4.732 * 1e-4)),
    QuantityConversion("gallon", (3.785 * 1e-3))
)
data class Volume(
    override val value: Double
) : Quantity(0, 3, 0, 0, value)
