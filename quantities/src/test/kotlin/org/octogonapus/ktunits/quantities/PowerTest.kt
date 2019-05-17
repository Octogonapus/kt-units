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
import org.octogonapus.ktunits.annotation.div
import org.octogonapus.ktunits.annotation.times

internal class PowerTest {

    @ParameterizedTest
    @MethodSource("electricPowerUnitsSource")
    fun `electric power units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    @Test
    fun `from test success`() {
        assertEquals(1.watt, Power.from(1.kilogram * 1.meter.pow(2) / 1.second.pow(3)))
    }

    @Test
    fun `from test failure`() {
        assertThrows<IllegalArgumentException> {
            Power.from(1.volt)
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun electricPowerUnitsSource() = listOf(
            Arguments.of(1e-12.watt, 1.picowatt),
            Arguments.of(1e-9.watt, 1.nanowatt),
            Arguments.of(1e-6.watt, 1.microwatt),
            Arguments.of(1e-3.watt, 1.milliwatt),
            Arguments.of(1e-2.watt, 1.centiwatt),
            Arguments.of(1e-1.watt, 1.deciwatt),
            Arguments.of(1.watt, 1.watt),
            Arguments.of(1e+1.watt, 1.decawatt),
            Arguments.of(1e+2.watt, 1.hectowatt),
            Arguments.of(1e+3.watt, 1.kilowatt),
            Arguments.of(1e+6.watt, 1.megawatt),
            Arguments.of(1e+9.watt, 1.gigawatt),
            Arguments.of(1e+12.watt, 1.terawatt),
            Arguments.of(1e+15.watt, 1.petawatt)
        )
    }
}
