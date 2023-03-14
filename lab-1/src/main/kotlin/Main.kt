import java.io.FileInputStream
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    try {

        val input: Scanner
        var isInteractive: Boolean = true
        var isRandom: Boolean = false
        var n: Int = 0

        if (args.isNotEmpty()) {
            when (args[0]) {
                "--random" -> {
                    if (args.size >= 2) {
                        isRandom = true
                        isInteractive = true
                        n = args[1].toIntOrNull() ?: run {
                            println("'n' value mustn't be empty")
                            return
                        }
                    } else {
                        throw IllegalArgumentException("Used '-f' option but no value specified")
                    }
                }

                "--file" -> {
                    if (args.size >= 2 && args[1].isNotEmpty()) {
                        isInteractive = false
                    } else {
                        throw IllegalArgumentException("Used '-f' option but no value specified")
                    }
                }

                "-h", "--help" -> {
                    println(
                        """
                    TODO: Docs
                """.trimIndent()
                    )
                    return
                }

                else -> throw IllegalArgumentException("Illegal option used. See '-h' flag.")
            }
        }
        input = if (isInteractive)
            Scanner(System.`in`)
        else {
            Scanner(FileInputStream(args[1])).useLocale(Locale.FRANCE)
        }

        if (isInteractive) {
            println(
                """
      #--------------------------------------------------------# 
      # Your are welcome in System of Linear Equations solver. #
      #--------------------------------------------------------#
    """.trimIndent()
            )
            println(
                """
        Please input your matrix dimension:
        ( 1 ≤ n ≤ 20 )
    """.trimIndent()
            )
            print(" n=")
        }


        if (!isRandom) {
            n = input.nextIntOrNull() ?: run {
                println("'n' value mustn't be empty")
                return
            }
        }

        if (n <= 0 || n > 20) {
            if (isInteractive) {
                println("'n' value must me in [1..20]")
                return
            } else {
                throw IllegalArgumentException("'n' value must me in [1..20]")
            }
        }


        if (isInteractive) {
            println(
                """
        Please, input your (n×n) values of matrix by rows of n elements separated with single space:
        NOTE. floating point is dot ","
    """.trimIndent()
            )
        }

        val matrix = MutableList(n) { DoubleArray(n) }
        val vector = DoubleArray(n)

        try {

            for (i in 0 until n) {
                for (j in 0 until n) {
                    matrix[i][j] = input.nextDoubleOrNull(isRandom) ?: run {
                        throw IllegalArgumentException("Illegal value")
                    }
                }
            }

            debug(matrix.pretty())

            if (isInteractive) {
                println(
                    """
            Thank you for your matrix.
            Input your values of right the system right part in one row separated with single space:
        """.trimIndent()
                )
            }

            for (i in 0 until n) {
                vector[i] = input.nextDoubleOrNull(isRandom) ?: run {
                    throw IllegalArgumentException("Illegal value")
                }
            }

            debug(vector.pretty())

        } catch (e: Exception) {
            if (isInteractive) {
                println("Wrong input! Be careful!")
                println("Reason: ${e.message}.")
                return
            } else {
                throw e
            }
        }

        val x = matrix.solveSLE(vector) ?: run {
            return
        }

        if (isInteractive) {
            println(
                """
        Result of calculations vector 'x':
    """.trimIndent()
            )
        }
        println(x.pretty())

        val r = DoubleArray(n) {
            vector[it] - matrix[it].foldIndexed(0.0) { idx, acc, d ->
                acc + d * x[idx]
            }
        }

        if (isInteractive) {
            println(
                """
            Vector of deviance:
        """.trimIndent()
            )
        }
        println(r.pretty())
    } catch (e: Exception) {
        println("Error appeared: ${e.message}")
        exitProcess(-1)
    }
}

fun debug(any: Any?) {
    return
    println("[DEBUG] $any")
}

fun Scanner.nextIntOrNull(isRandom: Boolean = false): Int? = if (isRandom) {
    Random.nextInt()
} else {
    try {
        this.nextInt()
    } catch (e: Exception) {
        null
    }
}

fun Scanner.nextDoubleOrNull(isRandom: Boolean = false): Double? = if (isRandom) {
    Random.nextDouble()
} else {
    try {
        this.nextDouble()
    } catch (e: Exception) {
        null
    }
}

fun Double.approx(double: Double): Boolean {
    return (this - double).absoluteValue < 1e-6
}

/**
 * extension function on matrix that stands for elements of system of linear equations.
 */
@OptIn(ExperimentalStdlibApi::class)
fun List<DoubleArray>.solveSLE(b: DoubleArray): DoubleArray? {
    val a = this
    val dim = a.size // matrix must be square unless we can slice square matrix from it
    assert(a.all { dim == it.size })
    assert(b.size == dim)

    val xs = DoubleArray(dim) { 0.0 }
    val tmp = a.copy().toMutableList()

    fun log(i: Int = -1, str: String = "") {
        return
        debug(">>>>>> $str $i  >>>>>>")
        debug(tmp.pretty())
        debug("### b:")
        debug(b.pretty())
        debug("### xs:")
        debug(xs.pretty())
        debug("<<<<<< $str $i  <<<<<<\n")
    }
    log()

    var nMutations = 0;

    // Forward
    for (x in 0..<dim - 1) {
        // swap rows
        if (tmp.mutateMatrixWithVector(vector = b, start = x)) {
            nMutations++
        }
        log(x, "forward")

        for (nextRow in x + 1..<dim) {
            val mul = -tmp[nextRow][x] / tmp[x][x]
            for (i in 0..<dim) {
                tmp[nextRow][i] += tmp[x][i] * mul
            }
            b[nextRow] += b[x] * mul

            tmp[nextRow][x] = 0.0 //must be 0
        }
        log(x, "forward")
    }

    println("Triangle matrix:")
    println(tmp.pretty())

    println("Det(Matrix)=")
    var det: Double = if (nMutations % 2 == 0) {
        1.0
    } else {
        -1.0
    }
    for (i in 0..<dim) {
        det *= tmp[i][i]
    }
    println(det)

    if (det.approx(0.0)) {
        println("Approximately zero determinant stands for 0 or ∞ solutions")
        return null
    }

    //Backward
    for (x in xs.indices.reversed()) {
        var tmpSum: Double = 0.0
        for (i in x + 1..xs.lastIndex) {
            tmpSum += tmp[x][i] * xs[i]
        }
        xs[x] = (b[x] - tmpSum) / tmp[x][x]
        log(x, "backward")
    }
    return xs
}


/**
 * Mute matrix to get greater element at first position
 */
@OptIn(ExperimentalStdlibApi::class)
fun MutableList<DoubleArray>.mutateMatrixWithVector(vector: DoubleArray, start: Int): Boolean {
    var mutated = false
    var greatest: Int = start
    for (i in start..<this.size) {
        if (this[i][start].absoluteValue > this[greatest][start].absoluteValue) {
            greatest = i
            mutated = true
        }
    }
    if (mutated) {
        this[start] = this[greatest].also { this[greatest] = this[start] }
        vector[start] = vector[greatest].also { vector[greatest] = vector[start] }
    }
    return mutated
}

fun List<DoubleArray>.pretty(): String = this.joinToString(
    prefix = "[", postfix = "]", separator = ",\n "
) { row ->
    row.joinToString(
        prefix = "[", postfix = "]", separator = ",\t"
    ) { el -> el.toString() }
}

fun DoubleArray.pretty() = this.joinToString(prefix = "[", separator = ",\n ", postfix = "]") { it.toString() }

inline fun List<DoubleArray>.copy() = this.map { it.clone() }