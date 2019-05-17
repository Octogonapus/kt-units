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
    QuantityConversion("nM", 1.0),
    QuantityConversion("kgFM", 0.102),
    QuantityConversion("lbFFt", 0.7376),
    QuantityConversion("lbFIn", 0.113),
    QuantityConversion("ozFIn", 7.062 * 1e-3)
)
data class Torque(
    override val value: Double
) : Quantity(timeDim = -2.0, lengthDim = 2.0, massDim = 1.0, value = value) {
    companion object {
        fun from(other: Quantity): Torque {
            if (other.dimensionsEqual(
                    lengthDim = 2.0,
                    massDim = 1.0,
                    timeDim = -2.0
                )
            ) {
                return Torque(other.value)
            } else {
                throw IllegalArgumentException(
                    """
                    |Cannot convert quantity to Torque:
                    |$other
                    """.trimMargin()
                )
            }
        }
    }
}
