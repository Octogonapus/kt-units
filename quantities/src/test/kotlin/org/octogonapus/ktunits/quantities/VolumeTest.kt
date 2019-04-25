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

internal class VolumeTest {

    @ParameterizedTest
    @MethodSource("volumeUnitsSource")
    fun `volume units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun volumeUnitsSource() = listOf(
            Arguments.of(1.cubicMeter, 1.cubicMeter),
            Arguments.of(1e-6.cubicMeter, 1.cubicCentimeter),
            Arguments.of((1.639 * 1e-5).cubicMeter, 1.cubicInch),
            Arguments.of((2.832 * 1e-2).cubicMeter, 1.cubicFoot),
            Arguments.of((7.646 * 1e-1).cubicMeter, 1.cubicYard),
            Arguments.of(1e-3.cubicMeter, 1.liter),
            Arguments.of((9.464 * 1e-4).cubicMeter, 1.quart),
            Arguments.of((4.732 * 1e-4).cubicMeter, 1.pint),
            Arguments.of((3.785 * 1e-3).cubicMeter, 1.gallon)
        )
    }
}
