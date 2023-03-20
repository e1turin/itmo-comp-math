import core.solveSLE
import core.toMutableMatrix
import input.nextDoubleOrNull
import input.nextIntOrNull
import output.pretty
import output.printSep
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess

const val docs: String = """
DESCRIPTION
    Utility for solving System of Liner Algebraic Equations
    
    NOTE: floating point is comma (",")

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
        var isInteractive = true
        var isRandom = false
        var n = 0

        if (args.isNotEmpty()) {
            when (args[0]) {
                "--random" -> {
                    if (args.size >= 2) {
                        isRandom = true
                        n = args[1].toIntOrNull() ?: run {
                            throw IllegalArgumentException("Invalid n input value")
                        }
                        if (n <= 0 || 20 < n) throw IllegalArgumentException("'n' value must me in [1..20]")
                    } else {
                        throw IllegalArgumentException("Used '--random' option but no dimension value specified")
                    }
                }

                "--file" -> {
                    if (args.size >= 2 && args[1].isNotEmpty()) {
                        isInteractive = false
                    } else {
                        throw IllegalArgumentException("Used '--file' option but no value specified")
                    }
                }

                "-h", "--help" -> {
                    println(docs)
                    return
                }

                else -> throw IllegalArgumentException("Illegal option is given. Use '-h' for help.")
            }
        }

        input = if (isInteractive) {
            Scanner(System.`in`)
        } else {
            Scanner(FileInputStream(args[1]))
        }.also {
            it.useLocale(Locale.FRANCE)
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
                ( 0 < n < 21 )
                """.trimIndent()
            )
            print(" n=")
        }

        if (!isRandom) {
            n = input.nextIntOrNull() ?: throw IOException("Illegal value")
        } else {
            print(" $n")
        }

        if (n <= 0 || 20 < n) throw IllegalArgumentException("'n' value must me in [1..20]")


        if (isInteractive) {
            printSep()
            println(
                """
                Please, input your (n x n) values of matrix by rows of n elements separated with single space:
                """.trimIndent()
            )
        }

        val matrix = MutableList(n) { DoubleArray(n) }.toMutableMatrix()
        val vector = DoubleArray(n)

        for (i in 0 until n) {
            for (j in 0 until n) {
                matrix.elements[i][j] =
                    input.nextDoubleOrNull(isRandom) ?: throw IOException("Illegal value")
            }
        }


        if (isInteractive) {
            printSep()
            println("Thank you for your matrix:")
            println(matrix.pretty())
            printSep()
            println(
                """
                Next step is input values of your system's right-hand part in one row separated with single space:
                """.trimIndent()
            )
        }

        for (i in 0 until n) {
            vector[i] = input.nextDoubleOrNull(isRandom) ?: throw IOException("Illegal value")
        }


        if (isInteractive) {
            printSep()
            println("Thank you for your vector:")
            println(vector.pretty())
        }

        val x =
            matrix.solveSLE(vector, logMiddleResults = true) ?: throw ArithmeticException("System could not be solved")

        printSep()
        println("Result of calculating vector 'x':")
        println(x.pretty())

        val r = DoubleArray(n) { i ->
            vector[i] - matrix.elements[i].foldIndexed(0.0) { j, acc, aij ->
                acc + aij * x[j]
            }
        }

        printSep()
        println("Vector of deviance 'r':")
        println(r.pretty())

    } catch (e: ArithmeticException) {
        println("Arithmetic error appeared: ${e.message}")
    } catch (e: Exception) {
        printSep()
        println("Error appeared: ${e.message}")
        exitProcess(-1)
    }
}
