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

internal class TimeTest {

    @ParameterizedTest
    @MethodSource("timeUnitsSource")
    fun `time units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    @Test
    fun `from test success`() {
        assertEquals(1.second, Time.from(1.meter / 1.mps))
    }

    @Test
    fun `from test failure`() {
        assertThrows<IllegalArgumentException> {
            Time.from(1.volt)
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun timeUnitsSource() = listOf(
            Arguments.of(1e+9.second, 1.gigasecond),
            Arguments.of(1e+6.second, 1.megasecond),
            Arguments.of(1e+3.second, 1.kilosecond),
            Arguments.of(1e+2.second, 1.hectosecond),
            Arguments.of(1e+1.second, 1.decasecond),
            Arguments.of(1.second, 1.second),
            Arguments.of(1e-1.second, 1.decisecond),
            Arguments.of(1e-2.second, 1.centisecond),
            Arguments.of(1e-3.second, 1.millisecond),
            Arguments.of(1e-6.second, 1.microsecond),
            Arguments.of(1e-9.second, 1.nanosecond)
        )
    }
}
