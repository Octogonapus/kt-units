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
package org.octogonapus.ktunits.annotation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class QuantityTest {

    @Test
    fun `test plus`() {
        assertEquals(
            Quantity(1, 1, 1, 1, 3.0),
            Quantity(1, 1, 1, 1, 1.0) +
                Quantity(1, 1, 1, 1, 2.0)
        )
    }

    @Test
    fun `test plus throws`() {
        assertThrows<UnsupportedOperationException> {
            Quantity(1, 1, 2, 1, 1.0) +
                Quantity(1, 1, 1, 1, 2.0)
        }
    }

    @Test
    fun `test minus`() {
        assertEquals(
            Quantity(1, 1, 1, 1, 2.0),
            Quantity(1, 1, 1, 1, 3.0) -
                Quantity(1, 1, 1, 1, 1.0)
        )
    }

    @Test
    fun `test minus throws`() {
        assertThrows<UnsupportedOperationException> {
            Quantity(1, 1, 1, 1, 3.0) -
                Quantity(1, 3, 1, 1, 1.0)
        }
    }

    @Test
    fun `test multiply`() {
        assertEquals(
            Quantity(2, 4, 6, 8, 25.0),
            Quantity(1, 2, 3, 4, 5.0) *
                Quantity(1, 2, 3, 4, 5.0)
        )
    }

    @Test
    fun `test divide`() {
        assertEquals(
            Quantity(0, 0, 1, 2, 3.0 / 2.0),
            Quantity(1, 2, 3, 4, 3.0) /
                Quantity(1, 2, 2, 2, 2.0)
        )
    }
}
