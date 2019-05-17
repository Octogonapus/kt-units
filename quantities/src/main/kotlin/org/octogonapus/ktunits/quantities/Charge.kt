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
import org.octogonapus.ktunits.annotation.QuantityBlacklist
import org.octogonapus.ktunits.annotation.QuantityConversion
import org.octogonapus.ktunits.annotation.QuantityConversions
import org.octogonapus.ktunits.annotation.QuantityType

@QuantityType(timeDim = 1.0, currentDim = 1.0)
@QuantityConversions(
    QuantityConversion("picocoulomb", 1e-12),
    QuantityConversion("nanocoulomb", 1e-9),
    QuantityConversion("microcoulomb", 1e-6),
    QuantityConversion("millicoulomb", 1e-3),
    QuantityConversion("coulomb", 1.0)
)
@QuantityBlacklist(Torque::class)
data class Charge(
    override val value: Double
) : Quantity(timeDim = 1.0, currentDim = 1.0, value = value) {
    companion object
}
