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
package org.octogonapus.ktunits.processor

import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element

internal data class ElementWithDimensions(
    val element: Element,
    val dimensions: DimensionData
) {

    val typeName by lazy {
        element.asType().asTypeName()
    }
}

internal fun ElementWithDimensions.isMultiplyCompatible(
    other: ElementWithDimensions,
    possibleReturnType: ElementWithDimensions
) = dimensions + other.dimensions == possibleReturnType.dimensions

internal fun ElementWithDimensions.isDivideCompatible(
    other: ElementWithDimensions,
    possibleReturnType: ElementWithDimensions
) = dimensions - other.dimensions == possibleReturnType.dimensions

internal fun ElementWithDimensions.isSqrtCompatible(
    other: ElementWithDimensions
) = dimensions / 2.0 == other.dimensions

internal fun ElementWithDimensions.isPowCompatible(
    other: ElementWithDimensions,
    power: Double
) = dimensions * power == other.dimensions
