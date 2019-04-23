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

val Number.gigagram get() = Mass(toDouble() * 1000000)
val Number.megagram get() = Mass(toDouble() * 1000)
val Number.kilogram get() = Mass(toDouble())
val Number.hectogram get() = Mass(toDouble() / 10)
val Number.decagram get() = Mass(toDouble() / 100)
val Number.gram get() = Mass(toDouble() / 1000)
val Number.decigram get() = Mass(toDouble() / 10000)
val Number.centigram get() = Mass(toDouble() / 100000)
val Number.milligram get() = Mass(toDouble() / 1000000)
val Number.microgram get() = Mass(toDouble() / 1000000000)
val Number.nanogram get() = Mass(toDouble() / 1000000000000)
val Number.lbM get() = Mass(toDouble() * 0.4536)
val Number.oz get() = Mass(toDouble() * 0.02835)

@QuantityType(1, 0, 0, 0)
data class Mass(
    override var value: Double
) : Quantity(1, 0, 0, 0, value)
