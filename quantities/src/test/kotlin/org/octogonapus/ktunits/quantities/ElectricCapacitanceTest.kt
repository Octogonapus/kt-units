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

internal class ElectricCapacitanceTest {

    @ParameterizedTest
    @MethodSource("electricCapacitanceUnitsSource")
    fun `electric capacitance units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
        val test: MagneticFlux = 1.joule / 1.ampere
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun electricCapacitanceUnitsSource() = listOf(
            Arguments.of(1e-12.farad, 1.picofarad),
            Arguments.of(1e-9.farad, 1.nanofarad),
            Arguments.of(1e-6.farad, 1.microfarad),
            Arguments.of(1e-3.farad, 1.millifarad),
            Arguments.of(1.farad, 1.farad)
        )
    }
}
