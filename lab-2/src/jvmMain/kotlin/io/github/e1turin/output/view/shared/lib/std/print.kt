package io.github.e1turin.output.view.shared.lib.std

fun Float.pretty(): String =
    String.format("%.04f", this)

fun Double.pretty(): String =
    String.format("%.04f", this)

fun ClosedRange<Double>.prettyRange(): String =
    "[ ${start.pretty()} .. ${endInclusive.pretty()} ]"

fun List<Double>.prettyVector(): String =
    joinToString(prefix = "[", separator = " ", postfix = "]") { it.pretty() }