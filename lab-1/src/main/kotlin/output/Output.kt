package output

import core.Matrix

fun List<DoubleArray>.pretty(): String = this.joinToString(
    prefix = "[", postfix = "]", separator = ",\n "
) { row ->
    row.joinToString(
        prefix = "[", postfix = "]", separator = ",\t"
    ) { el -> el.toString() }
}

fun DoubleArray.pretty() = joinToString(prefix = "[", separator = ",\n ", postfix = "]") { it.toString() }
fun Matrix.pretty() = elements.pretty()

fun printSep() = run { println("") }
