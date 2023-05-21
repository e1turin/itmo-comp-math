package output

import core.Matrix
import java.util.*

fun List<DoubleArray>.pretty(): String = this.joinToString(
    prefix = "[", postfix = "]", separator = ",\n "
) { row ->
    row.joinToString(
        prefix = "[", postfix = "]", separator = ",\t"
    ) {
        String.format(
            locale = Locale.FRANCE,
            format = "%.8f",
            it
        )
    }
}

fun DoubleArray.pretty() = joinToString(prefix = "[", separator = ",\n ", postfix = "]") {
    String.format(
        locale = Locale.FRANCE,
        format = "%.8f",
        it
    )
}

fun Matrix.pretty() = elements.pretty()

fun printSep() = run { println("") }
