package io.github.e1turin.shared.lib.std

fun Float.pretty(): String =
    if (!isNaN()) String.format("%.04f", this)
    else "?"

fun Double.pretty(): String =
    if (!isNaN()) String.format("%.04f", this)
    else "?"

fun DoubleArray.prettyVector(): String =
    joinToString(prefix = "[", separator = " ", postfix = "]") { it.pretty() }

fun ClosedRange<Double>.prettyRange(): String =
    "[ ${start.pretty()} .. ${endInclusive.pretty()} ]"

fun List<Double>.prettyVector(): String =
    joinToString(prefix = "[", separator = " ", postfix = "]") { it.pretty() }
