package org.octogonapus.ktunits.quantities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.octogonapus.ktunits.annotation.Quantity

internal class VelocityTest {

    @ParameterizedTest
    @MethodSource("velocityUnitsSource")
    fun `velocity units tests`(expected: Quantity, actual: Quantity) {
        assertEquals(expected, actual)
    }

    companion object {

        @Suppress("unused")
        @JvmStatic
        fun velocityUnitsSource() = listOf(
            Arguments.of(1.mps, 1.mps),
            Arguments.of(0.01.mps, 1.cmps),
            Arguments.of(2.236936.mps, 1.miph),
            Arguments.of(0.277778.mps, 1.kmph),
            Arguments.of(1000.mps, 1.kmps),
            Arguments.of(30.48.mps, 1.fps)
        )
    }
}
