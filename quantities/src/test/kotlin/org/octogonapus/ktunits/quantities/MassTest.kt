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
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.octogonapus.ktunits.annotation.Quantity

internal class MassTest {

    @ParameterizedTest
    @MethodSource("massUnitsSource")
    fun `mass units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    @Test
    fun `from test success`() {
        assertEquals(
            1.kilogram,
            Mass.from(1.newton / (1.mps / 1.second))
        )
    }

    @Test
    fun `from test failure`() {
        assertThrows<IllegalArgumentException> {
            Mass.from(1.volt)
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun massUnitsSource() = listOf(
            Arguments.of(1e-12.kilogram, 1.nanogram),
            Arguments.of(1e-9.kilogram, 1.microgram),
            Arguments.of(1e-6.kilogram, 1.milligram),
            Arguments.of(1e-5.kilogram, 1.centigram),
            Arguments.of(1e-4.kilogram, 1.decigram),
            Arguments.of(1e-3.kilogram, 1.gram),
            Arguments.of(1e-2.kilogram, 1.decagram),
            Arguments.of(1e-1.kilogram, 1.hectogram),
            Arguments.of(1.kilogram, 1.kilogram),
            Arguments.of(1e+3.kilogram, 1.megagram),
            Arguments.of(1e+6.kilogram, 1.gigagram),
            Arguments.of(0.4536.kilogram, 1.lbM),
            Arguments.of(0.02835.kilogram, 1.oz)
        )
    }
}
