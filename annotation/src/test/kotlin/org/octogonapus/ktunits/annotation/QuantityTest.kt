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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.acosh
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.atan
import kotlin.math.atanh
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.cosh
import kotlin.math.exp
import kotlin.math.expm1
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.ln1p
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.sinh
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.math.tanh
import kotlin.math.truncate
import kotlin.random.Random

internal class QuantityTest {

    private fun quantityWithDims(dim: Number, value: Number) = quantityWithDims({ dim }, value)

    private fun quantityWithDims(dimGenerator: (Int) -> Number, value: Number) = Quantity(
        currentDim = dimGenerator(1),
        tempDim = dimGenerator(2),
        timeDim = dimGenerator(3),
        lengthDim = dimGenerator(4),
        massDim = dimGenerator(5),
        luminDim = dimGenerator(6),
        moleDim = dimGenerator(7),
        angleDim = dimGenerator(8),
        value = value
    )

    private fun quantityWithRandomDims(value: Number = 0) = Random.Default.quantityWithRandomDims(value)

    private fun Random.quantityWithRandomDims(value: Number = 0) = Quantity(
        currentDim = nextDouble(),
        tempDim = nextDouble(),
        timeDim = nextDouble(),
        lengthDim = nextDouble(),
        massDim = nextDouble(),
        luminDim = nextDouble(),
        moleDim = nextDouble(),
        angleDim = nextDouble(),
        value = value.toDouble()
    )

    @Test
    fun `test plus`() {
        assertEquals(
            quantityWithDims(1, 3),
            quantityWithDims(1, 1) + quantityWithDims(1, 2)
        )
    }

    @Test
    fun `test plus throws`() {
        assertThrows<UnsupportedOperationException> {
            quantityWithRandomDims(1) + quantityWithRandomDims(2)
        }
    }

    @Test
    fun `test minus`() {
        assertEquals(
            quantityWithDims(1, 2),
            quantityWithDims(1, 3) - quantityWithDims(1, 1)
        )
    }

    @Test
    fun `test minus throws`() {
        assertThrows<UnsupportedOperationException> {
            quantityWithRandomDims(3) - quantityWithRandomDims(1)
        }
    }

    @Test
    fun `test multiply`() {
        assertEquals(
            quantityWithDims({ it * 2 }, 25),
            quantityWithDims({ it }, 5) * quantityWithDims({ it }, 5)
        )
    }

    @Test
    fun `test divide`() {
        assertEquals(
            quantityWithDims({ it }, 3.0 / 2.0),
            quantityWithDims({ it * 3 }, 3) / quantityWithDims({ it * 2 }, 2)
        )
    }

    @Test
    fun `test sin`() {
        val value = 1.3
        assertEquals(sin(value), quantityWithDims(0, value).sin())
    }

    @Test
    fun `test cos`() {
        val value = 1.3
        assertEquals(cos(value), quantityWithDims(0, value).cos())
    }

    @Test
    fun `test tan`() {
        val value = 1.3
        assertEquals(tan(value), quantityWithDims(0, value).tan())
    }

    @Test
    fun `test asin`() {
        val value = 1.3
        assertEquals(asin(value), quantityWithDims(0, value).asin())
    }

    @Test
    fun `test acos`() {
        val value = 1.3
        assertEquals(acos(value), quantityWithDims(0, value).acos())
    }

    @Test
    fun `test atan`() {
        val value = 1.3
        assertEquals(atan(value), quantityWithDims(0, value).atan())
    }

    @Test
    fun `test sinh`() {
        val value = 1.3
        assertEquals(sinh(value), quantityWithDims(0, value).sinh())
    }

    @Test
    fun `test cosh`() {
        val value = 1.3
        assertEquals(cosh(value), quantityWithDims(0, value).cosh())
    }

    @Test
    fun `test tanhh`() {
        val value = 1.3
        assertEquals(tanh(value), quantityWithDims(0, value).tanh())
    }

    @Test
    fun `test asinh`() {
        val value = 1.3
        assertEquals(asinh(value), quantityWithDims(0, value).asinh())
    }

    @Test
    fun `test acosh`() {
        val value = 1.3
        assertEquals(acosh(value), quantityWithDims(0, value).acosh())
    }

    @Test
    fun `test aatanhh`() {
        val value = 1.3
        assertEquals(atanh(value), quantityWithDims(0, value).atanh())
    }

    @Test
    fun `test sqrt`() {
        val value = 1.3
        assertEquals(
            quantityWithDims({ it }, sqrt(value)),
            quantityWithDims({ it * 2 }, value).sqrt()
        )
    }

    @Test
    fun `test pow`() {
        val value = 1.3
        assertEquals(
            quantityWithDims({ it * 4 }, value.pow(2)),
            quantityWithDims({ it * 2 }, value).pow(2)
        )
    }

    @Test
    fun `test exp`() {
        val value = 1.3
        assertEquals(exp(value), quantityWithDims(0, value).exp())
    }

    @Test
    fun `test expm1`() {
        val value = 1.3
        assertEquals(expm1(value), quantityWithDims(0, value).expm1())
    }

    @Test
    fun `test log`() {
        val value = 1.3
        val base = 3.0
        assertEquals(log(value, base), quantityWithDims(0, value).log(base))
    }

    @Test
    fun `test ln`() {
        val value = 1.3
        assertEquals(ln(value), quantityWithDims(0, value).ln())
    }

    @Test
    fun `test log10`() {
        val value = 1.3
        assertEquals(log10(value), quantityWithDims(0, value).log10())
    }

    @Test
    fun `test log2`() {
        val value = 1.3
        assertEquals(log2(value), quantityWithDims(0, value).log2())
    }

    @Test
    fun `test ln1p`() {
        val value = 1.3
        assertEquals(ln1p(value), quantityWithDims(0, value).ln1p())
    }

    @Test
    fun `test ceil`() {
        val value = 1.3
        assertEquals(
            quantityWithDims(1, ceil(value)),
            quantityWithDims(1, value).ceil()
        )
    }

    @Test
    fun `test floor`() {
        val value = 1.3
        assertEquals(
            quantityWithDims(1, floor(value)),
            quantityWithDims(1, value).floor()
        )
    }

    @Test
    fun `test truncate`() {
        val value = 1.3
        assertEquals(
            quantityWithDims(1, truncate(value)),
            quantityWithDims(1, value).truncate()
        )
    }

    @Test
    fun `test round`() {
        val value = 1.3
        assertEquals(
            quantityWithDims(1, round(value)),
            quantityWithDims(1, value).round()
        )
    }

    @Test
    fun `test abs`() {
        val value = 1.3
        assertEquals(
            quantityWithDims(1, abs(value)),
            quantityWithDims(1, value).abs()
        )
    }

    @Test
    fun `test sign`() {
        val value = 1.3
        assertEquals(sign(value), quantityWithDims(0, value).sign())
    }

    @ParameterizedTest
    @MethodSource("cutRangeSource")
    fun `test cutRange`(value: Double, min: Double, max: Double, expected: Double) {
        assertEquals(
            quantityWithDims(0, expected),
            quantityWithDims(0, value).cutRange(min, max)
        )
    }

    @SuppressWarnings("LongParameterList")
    @ParameterizedTest
    @MethodSource("mapSource")
    fun `test map`(
        value: Double,
        oldMin: Double,
        oldMax: Double,
        newMin: Double,
        newMax: Double,
        expectedValue: Double
    ) {
        val expected = quantityWithDims(0, expectedValue)
        val actual = quantityWithDims(0, value).map(oldMin, oldMax, newMin, newMax)
        assertAll(
            { assertTrue(expected.dimensionsEqual(actual)) },
            { assertEquals(expected.value, actual.value, 1e-10) }
        )
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun cutRangeSource() = listOf(
            Arguments.of(1, -2, 2, 2),
            Arguments.of(2, -2, 2, 2),
            Arguments.of(0, -2, 2, 2),
            Arguments.of(-2, -2, 2, -2),
            Arguments.of(-3, -2, 2, -3),
            Arguments.of(3, -2, 2, 3)
        )

        @Suppress("unused")
        @JvmStatic
        fun mapSource() = listOf(
            Arguments.of(0, -1, 1, -2, 2, 0),
            Arguments.of(0.1, -1, 1, -2, 2, 0.2),
            Arguments.of(-0.1, -1, 1, 2, -2, 0.2),
            Arguments.of(0, -1, 1, -5, 2, -1.5)
        )
    }
}
