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

@QuantityType(timeDim = 1.0)
@QuantityConversions(
    QuantityConversion("gigasecond", 1e+9),
    QuantityConversion("megasecond", 1e+6),
    QuantityConversion("kilosecond", 1e+3),
    QuantityConversion("hectosecond", 1e+2),
    QuantityConversion("decasecond", 1e+1),
    QuantityConversion("second", 1.0),
    QuantityConversion("decisecond", 1e-1),
    QuantityConversion("centisecond", 1e-2),
    QuantityConversion("millisecond", 1e-3),
    QuantityConversion("microsecond", 1e-6),
    QuantityConversion("nanosecond", 1e-9),
    QuantityConversion("hour", 3600.0),
    QuantityConversion("minute", 60.0)
)
@QuantityBlacklist(Torque::class)
data class Time(
    override val value: Double
) : Quantity(timeDim = 1.0, value = value) {
    companion object
}
