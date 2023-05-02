package io.github.e1turin.output.view.shared.lib.plot

import io.github.e1turin.output.view.entities.plot.model.Gu
import io.github.e1turin.output.view.entities.plot.ui.gu

fun ClosedRange<Double>.toGu(): ClosedRange<Gu> =
    start.gu..endInclusive.gu
