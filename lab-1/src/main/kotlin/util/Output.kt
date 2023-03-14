package util

fun List<DoubleArray>.pretty(): String = this.joinToString(
    prefix = "[", postfix = "]", separator = ",\n "
) { row ->
    row.joinToString(
        prefix = "[", postfix = "]", separator = ",\t"
    ) { el -> el.toString() }
}

fun DoubleArray.pretty() = this.joinToString(prefix = "[", separator = ",\n ", postfix = "]") { it.toString() }

fun printSep() = run { println("") }
