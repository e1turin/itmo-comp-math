package io.github.e1turin.output.view.shared.lib.plot

import androidx.compose.runtime.Stable
import io.github.e1turin.output.view.entities.plot.model.Gu

fun ClosedRange<Double>.toGu(): ClosedRange<Gu> =
    start.gu..endInclusive.gu

@Stable
inline val Float.gu: Gu get() = Gu(this)

@Stable
inline val Double.gu: Gu get() = Gu(this.toFloat())

@Stable
inline val Int.gu: Gu get() = Gu(this.toFloat())

