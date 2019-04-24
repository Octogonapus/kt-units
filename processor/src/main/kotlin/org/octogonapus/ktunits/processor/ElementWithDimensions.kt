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

import javax.lang.model.element.Element

internal data class ElementWithDimensions(
    val element: Element,
    val dimensions: DimensionData
)

internal fun ElementWithDimensions.isMultiplyCompatible(
    other: ElementWithDimensions,
    possibleReturnTypes: List<ElementWithDimensions>
) = possibleReturnTypes.filter { dimensions + other.dimensions == it.dimensions }

internal fun ElementWithDimensions.isDivideCompatible(
    other: ElementWithDimensions,
    possibleReturnTypes: List<ElementWithDimensions>
) = possibleReturnTypes.filter { dimensions - other.dimensions == it.dimensions }
