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

internal class AngleTest {

    @ParameterizedTest
    @MethodSource("unitsSource")
    fun `units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    @Test
    fun `from test success`() {
        assertEquals(1.radian, (180 / PI).degree)
    }

    @Test
    fun `from test failure`() {
        assertThrows<IllegalArgumentException> {
            Angle.from(1.meter)
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun unitsSource() = listOf(
            Arguments.of(1.radian, 1.radian),
            Arguments.of(1e-3.radian, 1.milliradian),
            Arguments.of((2 * PI).radian, 1.revolution),
            Arguments.of((PI / 180).radian, 1.degree),
            Arguments.of((PI / 1.08e+4).radian, 1.arcminute),
            Arguments.of((PI / 6.48e+5).radian, 1.arcsecond),
            Arguments.of((PI / 6.48e+8).radian, 1.milliarcsecond),
            Arguments.of((PI / 200).radian, 1.gradian)
        )
    }
}
