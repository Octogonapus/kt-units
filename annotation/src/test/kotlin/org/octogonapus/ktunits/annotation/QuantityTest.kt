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
import kotlin.math.acos
import kotlin.math.acosh
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.atan
import kotlin.math.atanh
import kotlin.math.cos
import kotlin.math.cosh
import kotlin.math.exp
import kotlin.math.expm1
import kotlin.math.ln
import kotlin.math.ln1p
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.log2
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.sinh
import kotlin.math.tan
import kotlin.math.tanh

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

    @Test
    fun `test sin`() {
        val value = 1.3
        assertEquals(sin(value), Quantity(0, 0, 0, 0, value).sin())
    }

    @Test
    fun `test cos`() {
        val value = 1.3
        assertEquals(cos(value), Quantity(0, 0, 0, 0, value).cos())
    }

    @Test
    fun `test tan`() {
        val value = 1.3
        assertEquals(tan(value), Quantity(0, 0, 0, 0, value).tan())
    }

    @Test
    fun `test asin`() {
        val value = 1.3
        assertEquals(asin(value), Quantity(0, 0, 0, 0, value).asin())
    }

    @Test
    fun `test acos`() {
        val value = 1.3
        assertEquals(acos(value), Quantity(0, 0, 0, 0, value).acos())
    }

    @Test
    fun `test atan`() {
        val value = 1.3
        assertEquals(atan(value), Quantity(0, 0, 0, 0, value).atan())
    }

    @Test
    fun `test sinh`() {
        val value = 1.3
        assertEquals(sinh(value), Quantity(0, 0, 0, 0, value).sinh())
    }

    @Test
    fun `test cosh`() {
        val value = 1.3
        assertEquals(cosh(value), Quantity(0, 0, 0, 0, value).cosh())
    }

    @Test
    fun `test tanhh`() {
        val value = 1.3
        assertEquals(tanh(value), Quantity(0, 0, 0, 0, value).tanh())
    }

    @Test
    fun `test asinh`() {
        val value = 1.3
        assertEquals(asinh(value), Quantity(0, 0, 0, 0, value).asinh())
    }

    @Test
    fun `test acosh`() {
        val value = 1.3
        assertEquals(acosh(value), Quantity(0, 0, 0, 0, value).acosh())
    }

    @Test
    fun `test aatanhh`() {
        val value = 1.3
        assertEquals(atanh(value), Quantity(0, 0, 0, 0, value).atanh())
    }

    // @Test
    // fun `test sqrt`() {
    //     val value = 1.3
    //     assertEquals(sqrt(value), Quantity(0, 0, 0, 0, value).sqrt())
    // }

    @Test
    fun `test exp`() {
        val value = 1.3
        assertEquals(exp(value), Quantity(0, 0, 0, 0, value).exp())
    }

    @Test
    fun `test expm1`() {
        val value = 1.3
        assertEquals(expm1(value), Quantity(0, 0, 0, 0, value).expm1())
    }

    @Test
    fun `test log`() {
        val value = 1.3
        val base = 3.0
        assertEquals(log(value, base), Quantity(0, 0, 0, 0, value).log(base))
    }

    @Test
    fun `test ln`() {
        val value = 1.3
        assertEquals(ln(value), Quantity(0, 0, 0, 0, value).ln())
    }

    @Test
    fun `test log10`() {
        val value = 1.3
        assertEquals(log10(value), Quantity(0, 0, 0, 0, value).log10())
    }

    @Test
    fun `test log2`() {
        val value = 1.3
        assertEquals(log2(value), Quantity(0, 0, 0, 0, value).log2())
    }

    @Test
    fun `test ln1p`() {
        val value = 1.3
        assertEquals(ln1p(value), Quantity(0, 0, 0, 0, value).ln1p())
    }

    // @Test
    // fun `test ceil`() {
    //     val value = 1.3
    //     assertEquals(ceil(value), Quantity(0, 0, 0, 0, value).ceil())
    // }
    //
    // @Test
    // fun `test floor`() {
    //     val value = 1.3
    //     assertEquals(floor(value), Quantity(0, 0, 0, 0, value).floor())
    // }
    //
    // @Test
    // fun `test truncate`() {
    //     val value = 1.3
    //     assertEquals(truncate(value), Quantity(0, 0, 0, 0, value).truncate())
    // }
    //
    // @Test
    // fun `test round`() {
    //     val value = 1.3
    //     assertEquals(round(value), Quantity(0, 0, 0, 0, value).round())
    // }
    //
    // @Test
    // fun `test abs`() {
    //     val value = 1.3
    //     assertEquals(abs(value), Quantity(0, 0, 0, 0, value).abs())
    // }

    @Test
    fun `test sign`() {
        val value = 1.3
        assertEquals(sign(value), Quantity(0, 0, 0, 0, value).sign())
    }
}
