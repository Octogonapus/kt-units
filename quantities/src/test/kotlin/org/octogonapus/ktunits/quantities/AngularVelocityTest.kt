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
import kotlin.math.PI

internal class AngularVelocityTest {

    @ParameterizedTest
    @MethodSource("unitsSource")
    fun `units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    @Test
    fun `from test success`() {
        assertEquals(1.radianPerSecond, 1.radian / 1.second)
    }

    @Test
    fun `from test failure`() {
        assertThrows<IllegalArgumentException> {
            AngularVelocity.from(1.meter)
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun unitsSource() = listOf(
            Arguments.of(1.radianPerSecond, 1.radianPerSecond),
            Arguments.of(0.01667.radianPerSecond, 1.radianPerMinute),
            Arguments.of(2.778e-4.radianPerSecond, 1.radianPerHour),
            Arguments.of(0.01745.radianPerSecond, 1.degreePerSecond),
            Arguments.of(2.909e-4.radianPerSecond, 1.degreePerMinute),
            Arguments.of(4.848e-6.radianPerSecond, 1.degreePerHour),
            Arguments.of((PI * 2).radianPerSecond, 1.revolutionPerSecond),
            Arguments.of((PI * 2 / 60.0).radianPerSecond, 1.revolutionPerMinute),
            Arguments.of((PI * 2 / 3600.0).radianPerSecond, 1.revolutionPerHour)
        )
    }
}
