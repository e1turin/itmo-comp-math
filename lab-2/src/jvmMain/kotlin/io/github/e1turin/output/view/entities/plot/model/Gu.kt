package io.github.e1turin.output.view.entities.plot.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

/**
 * Dimension value representing graph units (gu). [value] is units of canvas in pixels
 */
@Immutable
@JvmInline
value class Gu(val value: Float) : Comparable<Gu> {

    /**
     * Add two [Gu]s together.
     */
    @Stable
    inline operator fun plus(other: Gu) =
        Gu(value = this.value + other.value)

    /**
     * Subtract a [Gu] from another one.
     */
    @Stable
    inline operator fun minus(other: Gu) =
        Gu(value = this.value - other.value)

    /**
     * This is the same as multiplying the [Gu] by -1.0.
     */
    @Stable
    inline operator fun unaryMinus() = Gu(-value)

    /**
     * Divide a [Gu] by a scalar.
     */
    @Stable
    inline operator fun div(other: Float) =
        Gu(value = value / other)

    @Stable
    inline operator fun div(other: Int) =
        Gu(value = value / other)

    /**
     * Divide by another [Gu] to get a scalar.
     */
    @Stable
    inline operator fun div(other: Gu): Float = value / other.value

    /**
     * Multiply a [Gu] by a scalar.
     */
    @Stable
    inline operator fun times(other: Float) =
        Gu(value = value * other)

    @Stable
    inline operator fun times(other: Int) =
        Gu(value = value * other)

    /**
     * Support comparing Dimensions with comparison operators.
     */
    @Stable
    override /* TODO: inline */ operator fun compareTo(other: Gu) =
        value.compareTo(other.value)

    @Stable
    override fun toString() = if (isUnspecified) "Gu.Unspecified" else "$value.gu"

    companion object {
        /**
         * A dimension used to represent a hairline drawing element. Hairline elements take up no
         * space, but will draw a single pixel, independent of the device's resolution and density.
         */
        @Stable
        val Hairline = Gu(value = 0f)

        /**
         * Infinite dp dimension.
         */
        @Stable
        val Infinity = Gu(value = Float.POSITIVE_INFINITY)

        /**
         * Constant that means unspecified Dp
         */
        @Stable
        val Unspecified = Gu(value = Float.NaN)
    }
}

/**
 * `false` when this is [Gu.Unspecified].
 */
@Stable
inline val Gu.isSpecified: Boolean
    get() = !value.isNaN()

/**
 * `true` when this is [Gu.Unspecified].
 */
@Stable
inline val Gu.isUnspecified: Boolean
    get() = value.isNaN()

