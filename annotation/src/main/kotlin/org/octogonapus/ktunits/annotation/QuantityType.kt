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
 * This annotation MUST be applied ONLY to classes which inherit from [Quantity]. The dimensions
 * given to this annotation should match exactly those given to the [Quantity] constructor.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class QuantityType(
    val currentDim: Double = 0.0,
    val tempDim: Double = 0.0,
    val timeDim: Double = 0.0,
    val lengthDim: Double = 0.0,
    val massDim: Double = 0.0,
    val luminDim: Double = 0.0,
    val moleDim: Double = 0.0,
    val angleDim: Double = 0.0
)
