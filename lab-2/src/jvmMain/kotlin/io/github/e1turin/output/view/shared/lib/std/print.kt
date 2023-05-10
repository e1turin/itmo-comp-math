package io.github.e1turin.output.view.shared.lib.std

fun Float.pretty(): String =
    String.format("%.04f", this)

fun Double.pretty(): String =
    String.format("%.04f", this)

fun ClosedRange<Double>.pretty(): String =
    "[ ${start.pretty()} .. ${endInclusive.pretty()} ]"