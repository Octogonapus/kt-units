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

/**
 * @param name The name of the conversion
 * @param ratio The conversion ratio.
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Repeatable
annotation class QuantityConversion(
    val name: String,
    val ratio: Double
)

/**
 * This annotation MUST be applied ONLY to classes which inherit from [Quantity] AND are also
 * annotated with [QuantityType].
 *
 * This is a workaround because of this issue: https://youtrack.jetbrains.com/issue/KT-12794
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class QuantityConversions(
    vararg val values: QuantityConversion
)
