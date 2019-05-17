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

import kotlin.reflect.KClass

/**
 * This annotation MUST be applied ONLY to classes which inherit from [Quantity] AND are also
 * annotated with [QuantityType].
 *
 * This signals to the annotation processor that generated methods which return one of
 * [blacklistedClasses] cannot not be `operator`. The annotation processor will instead generate
 * `timesFoo` and/or `divFoo` methods for a given class `Foo`.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class QuantityBlacklist(
    vararg val blacklistedClasses: KClass<out Quantity>
)
