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

@QuantityType(lengthDim = 2.0, massDim = 1.0, timeDim = -3.0)
@QuantityConversions(
    QuantityConversion("picowatt", 1e-12),
    QuantityConversion("nanowatt", 1e-9),
    QuantityConversion("microwatt", 1e-6),
    QuantityConversion("milliwatt", 1e-3),
    QuantityConversion("centiwatt", 1e-2),
    QuantityConversion("deciwatt", 1e-1),
    QuantityConversion("watt", 1.0),
    QuantityConversion("decawatt", 1e+1),
    QuantityConversion("hectowatt", 1e+2),
    QuantityConversion("kilowatt", 1e+3),
    QuantityConversion("megawatt", 1e+6),
    QuantityConversion("gigawatt", 1e+9),
    QuantityConversion("terawatt", 1e+12),
    QuantityConversion("petawatt", 1e+15)
)
@QuantityBlacklist(Torque::class)
data class Power(
    override val value: Double
) : Quantity(lengthDim = 2.0, massDim = 1.0, timeDim = -3.0, value = value) {
    companion object {
        fun from(other: Quantity): Power {
            if (other.dimensionsEqual(
                    lengthDim = 2.0,
                    massDim = 1.0,
                    timeDim = -3.0
                )
            ) {
                return Power(other.value)
            } else {
                throw IllegalArgumentException(
                    """
                    |Cannot convert quantity to Power:
                    |$other
                    """.trimMargin()
                )
            }
        }
    }
}
