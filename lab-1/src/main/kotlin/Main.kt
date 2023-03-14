import util.*
import java.io.FileInputStream
import java.util.*
import kotlin.system.exitProcess

const val docs: String = """
DESCRIPTION
    Utility for solving System of Liner Algebraic Equations

SYNOPSIS 
    solve [FLAG [PARAM]] 
    
FLAG
    -h, --help          Prints help doc of utility;
    
    --file      [path]  Receives `path` to the input file, works in not interactive mode;
    
    --random    [n]     Receives number of matrix dimensions `n`;
"""

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
                    println(docs)
                    return
                }

                else -> throw IllegalArgumentException("Illegal option used. See '-h' flag.")
            }
        }

        input = if (isInteractive) Scanner(System.`in`)
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
            printSep()
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
                printSep()
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
                printSep()
                println("Wrong input! Be careful!")
                println("Reason: ${e.message}.")
                return
            } else {
                throw e
            }
        }

        val x = matrix.solveSLE(vector, logMiddleResults = false) ?: run {
            return
        }

        printSep()
        println(
            """
        Result of calculating vector 'x':
    """.trimIndent()
        )
        println(x.pretty())

        val r = DoubleArray(n) {
            vector[it] - matrix[it].foldIndexed(0.0) { idx, acc, d ->
                acc + d * x[idx]
            }
        }

        printSep()
        println(
            """
            Vector of deviance 'r':
        """.trimIndent()
        )
        println(r.pretty())

    } catch (e: Exception) {
        printSep()
        println("Error appeared: ${e.message}")
        exitProcess(-1)
    }
}
