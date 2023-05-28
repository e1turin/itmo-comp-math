package io.github.e1turin.shared.lib

fun Double.pretty(): String {
    return if (this.isFinite()) String.format("%.6f", this)
    else "{ no convergence: $this }"
}