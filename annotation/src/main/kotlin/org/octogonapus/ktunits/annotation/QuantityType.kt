package org.octogonapus.ktunits.annotation

/**
 * This annotation MUST be applied ONLY to classes which inherit from [Quantity]. The dimensions
 * given to this annotation should match exactly those given to the [Quantity] constructor.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class QuantityType(
    val massDim: Long,
    val lengthDim: Long,
    val timeDim: Long,
    val angleDim: Long
)
