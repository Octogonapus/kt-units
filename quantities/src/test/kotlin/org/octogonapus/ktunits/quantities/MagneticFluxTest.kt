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

internal class MagneticFluxTest {

    @ParameterizedTest
    @MethodSource("magneticFluxUnitsSource")
    fun `magnetic flux units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun magneticFluxUnitsSource() = listOf(
            Arguments.of(1e-9.weber, 1.nanoweber),
            Arguments.of(1e-6.weber, 1.microweber),
            Arguments.of(1e-3.weber, 1.milliweber),
            Arguments.of(1.weber, 1.weber)
        )
    }
}
