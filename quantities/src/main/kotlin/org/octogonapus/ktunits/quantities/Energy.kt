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

@QuantityType(timeDim = -2.0, lengthDim = 2.0, massDim = 1.0)
@QuantityConversions(
    QuantityConversion("picojoule", 1e-12),
    QuantityConversion("nanojoule", 1e-9),
    QuantityConversion("microjoule", 1e-6),
    QuantityConversion("millijoule", 1e-3),
    QuantityConversion("joule", 1.0),
    QuantityConversion("kilojoule", 1e+3),
    QuantityConversion("megajoule", 1e+6),
    QuantityConversion("gigajoule", 1e+9),
    QuantityConversion("terajoule", 1e+12)
)
data class Energy(
    override val value: Double
) : Quantity(timeDim = -2.0, lengthDim = 2.0, massDim = 1.0, value = value) {
    companion object
}
