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

internal class TimeTest {

    @ParameterizedTest
    @MethodSource("timeUnitsSource")
    fun `time units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun timeUnitsSource() = listOf(
            Arguments.of(100000.second, 1.gigasecond),
            Arguments.of(10000.second, 1.megasecond),
            Arguments.of(1000.second, 1.kilosecond),
            Arguments.of(100.second, 1.hectosecond),
            Arguments.of(10.second, 1.decasecond),
            Arguments.of(1.second, 1.second),
            Arguments.of(0.1.second, 1.decisecond),
            Arguments.of(0.01.second, 1.centisecond),
            Arguments.of(0.001.second, 1.millisecond),
            Arguments.of(0.0001.second, 1.microsecond),
            Arguments.of(0.00001.second, 1.nanosecond)
        )
    }
}
