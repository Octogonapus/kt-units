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

val Number.gigameter get() = Length(toDouble() * 1000000000)
val Number.megameter get() = Length(toDouble() * 1000000)
val Number.kilometer get() = Length(toDouble() * 1000)
val Number.hectometer get() = Length(toDouble() * 100)
val Number.decameter get() = Length(toDouble() * 10)
val Number.meter get() = Length(toDouble())
val Number.decimeter get() = Length(toDouble() / 10)
val Number.centimeter get() = Length(toDouble() / 100)
val Number.millimeter get() = Length(toDouble() / 1000)
val Number.micrometer get() = Length(toDouble() / 1000000)
val Number.nanometer get() = Length(toDouble() / 1000000000)
val Number.yard get() = Length(toDouble() * 0.9144)
val Number.inch get() = Length(toDouble() * 0.0254)
val Number.foot get() = Length(toDouble() * 0.3048)
val Number.mile get() = Length(toDouble() * 1609.344)

@QuantityType(0, 1, 0, 0)
data class Length(
    override var value: Double
) : Quantity(0, 1, 0, 0, value)
