/*
 * This file is part of bowler-kernel.
 *
 * bowler-kernel is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * bowler-kernel is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with bowler-kernel.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.octogonapus.ktunits.quantities

import org.octogonapus.ktunits.annotation.Quantity
import org.octogonapus.ktunits.annotation.QuantityType

val Number.meter
    get() = Length(toDouble())

val Number.centimeter
    get() = Length(toDouble() / 100)

@QuantityType(0, 1, 0, 0)
data class Length(
    override var value: Double
) : Quantity(0, 1, 0, 0, value)

@QuantityType(0, 2, 0, 0)
data class Area(
    override var value: Double
) : Quantity(0, 2, 0, 0, value)

val Number.second
    get() = Time(toDouble())

@QuantityType(0, 0, 1, 0)
data class Time(
    override var value: Double
) : Quantity(0, 0, 1, 0, value)

val Number.mps
    get() = Velocity(toDouble())

@QuantityType(0, 1, -1, 0)
data class Velocity(
    override var value: Double
) : Quantity(0, 1, -1, 0, value)
