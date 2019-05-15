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

@QuantityType(1.0, 0.0, 0.0, 0.0)
@QuantityConversions(
    QuantityConversion("gigagram", 1e+6),
    QuantityConversion("megagram", 1e+3),
    QuantityConversion("kilogram", 1.0),
    QuantityConversion("hectogram", 1e-1),
    QuantityConversion("decagram", 1e-2),
    QuantityConversion("gram", 1e-3),
    QuantityConversion("decigram", 1e-4),
    QuantityConversion("centigram", 1e-5),
    QuantityConversion("milligram", 1e-6),
    QuantityConversion("microgram", 1e-9),
    QuantityConversion("nanogram", 1e-12),
    QuantityConversion("lbM", 0.4536),
    QuantityConversion("oz", 0.02835)
)
data class Mass(
    override val value: Double
) : Quantity(1, 0, 0, 0, value)
