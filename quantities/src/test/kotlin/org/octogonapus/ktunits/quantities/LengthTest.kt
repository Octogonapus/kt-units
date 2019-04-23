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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.octogonapus.ktunits.annotation.Quantity

internal class LengthTest {

    @ParameterizedTest
    @MethodSource("lengthUnitsSource")
    fun `length units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun lengthUnitsSource() = listOf(
            Arguments.of(1000000000.meter, 1.gigameter),
            Arguments.of(1000000.meter, 1.megameter),
            Arguments.of(1000.meter, 1.kilometer),
            Arguments.of(100.meter, 1.hectometer),
            Arguments.of(10.meter, 1.decameter),
            Arguments.of(1.meter, 1.meter),
            Arguments.of(0.1.meter, 1.decimeter),
            Arguments.of(0.01.meter, 1.centimeter),
            Arguments.of(0.001.meter, 1.millimeter),
            Arguments.of(0.000001.meter, 1.micrometer),
            Arguments.of(0.000000001.meter, 1.nanometer),
            Arguments.of(0.9144.meter, 1.yard),
            Arguments.of(0.0254.meter, 1.inch),
            Arguments.of(0.3048.meter, 1.foot),
            Arguments.of(1609.344.meter, 1.mile)
        )
    }
}
