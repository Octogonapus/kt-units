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

val Number.gigasecond get() = Time(toDouble() * 1000000000)
val Number.megasecond get() = Time(toDouble() * 1000000)
val Number.kilosecond get() = Time(toDouble() * 1000)
val Number.hectosecond get() = Time(toDouble() * 100)
val Number.decasecond get() = Time(toDouble() * 10)
val Number.second get() = Time(toDouble())
val Number.decisecond get() = Time(toDouble() / 10)
val Number.centisecond get() = Time(toDouble() / 100)
val Number.millisecond get() = Time(toDouble() / 1000)
val Number.microsecond get() = Time(toDouble() / 1000000)
val Number.nanosecond get() = Time(toDouble() / 1000000000)

@QuantityType(0, 0, 1, 0)
data class Time(
    override var value: Double
) : Quantity(0, 0, 1, 0, value)
