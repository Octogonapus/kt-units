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

internal class ChargeTest {

    @ParameterizedTest
    @MethodSource("chargeUnitsSource")
    fun `charge units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    @Test
    fun `from test success`() {
        assertEquals(
            1.coulomb,
            Charge.from(
                (1.second * 1.ampere)
            )
        )
    }

    @Test
    fun `from test failure`() {
        assertThrows<IllegalArgumentException> {
            Charge.from(1.volt)
        }
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun chargeUnitsSource() = listOf(
            Arguments.of(1e-12.coulomb, 1.picocoulomb),
            Arguments.of(1e-9.coulomb, 1.nanocoulomb),
            Arguments.of(1e-6.coulomb, 1.microcoulomb),
            Arguments.of(1e-3.coulomb, 1.millicoulomb),
            Arguments.of(1.coulomb, 1.coulomb)
        )
    }
}
