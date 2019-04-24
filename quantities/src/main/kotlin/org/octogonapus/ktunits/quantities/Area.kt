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
import org.octogonapus.ktunits.annotation.QuantityType

val Number.sqMeter get() = Area(toDouble())
val Number.sqInch get() = Area(toDouble() * (6.452 * 1e-4))
val Number.sqCentimeter get() = Area(toDouble() / 1e+4)
val Number.sqYard get() = Area(toDouble() * 0.8361)
val Number.sqMile get() = Area(toDouble() * (2.59 * 1e+6))

@QuantityType(0, 2, 0, 0)
data class Area(
    override var value: Double
) : Quantity(0, 2, 0, 0, value)
