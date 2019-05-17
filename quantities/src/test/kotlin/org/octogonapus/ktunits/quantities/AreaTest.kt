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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.octogonapus.ktunits.annotation.Quantity
import org.octogonapus.ktunits.annotation.pow

internal class AreaTest {

    @ParameterizedTest
    @MethodSource("areaUnitsSource")
    fun `area units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    @Test
    fun `from test success`() {
        assertEquals(1.sqMeter, Area.from(1.meter.pow(2)))
    }

    @Test
    fun `from test failure`() {
        assertThrows<IllegalArgumentException> {
            Area.from(1.meter)
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun areaUnitsSource() = listOf(
            Arguments.of(1.sqMeter, 1.sqMeter),
            Arguments.of((6.452 * 1e-4).sqMeter, 1.sqInch),
            Arguments.of(1e-4.sqMeter, 1.sqCentimeter),
            Arguments.of(0.8361.sqMeter, 1.sqYard),
            Arguments.of((2.59 * 1e+6).sqMeter, 1.sqMile)
        )
    }
}
