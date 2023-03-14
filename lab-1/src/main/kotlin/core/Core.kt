package core

import out.pretty
import out.printSep
import kotlin.math.absoluteValue

fun Double.approx(double: Double): Boolean {
    return (this - double).absoluteValue < 1e-6
}

/**
 * extension function on matrix that stands for elements of system of linear equations.
 */
@OptIn(ExperimentalStdlibApi::class)
fun Matrix.solveSLE(b: DoubleArray, logMiddleResults: Boolean = false): DoubleArray? {
    val dim = elements.size // matrix must be square unless we can slice square matrix from it
    assert(elements.all { dim == it.size })
    assert(b.size == dim)

    val xs = DoubleArray(dim) { 0.0 }
    val tmp = elements.copy().toMutableMatrix()

    var nMutations = 0;

    // Forward
    for (x in 0..<dim - 1) {
        // swap rows
        if (tmp.mutateMatrixWithVector(vector = b, start = x)) {
            nMutations++
        }

        for (nextRow in x + 1..<dim) {
            val mul = -tmp.elements[nextRow][x] / tmp.elements[x][x]
            for (i in 0..<dim) {
                tmp.elements[nextRow][i] += tmp.elements[x][i] * mul
            }
            b[nextRow] += b[x] * mul

            tmp.elements[nextRow][x] = 0.0 //must be 0
        }
    }

    if (logMiddleResults) {
        println("Triangle matrix:")
        println(tmp.pretty())
    }

    var det: Double = if (nMutations % 2 == 0) {
        1.0
    } else {
        -1.0
    }
    for (i in 0..<dim) {
        det *= tmp.elements[i][i]
    }
    if (logMiddleResults) {
        printSep()
        println("Det(Matrix)=")
        println(det)
    }

    if (det.approx(0.0)) {
        println("Approximately zero determinant stands for 0 or ∞ solutions")
        return null
    }

    //Backward
    for (x in xs.indices.reversed()) {
        var tmpSum: Double = 0.0
        for (i in x + 1..xs.lastIndex) {
            tmpSum += tmp.elements[x][i] * xs[i]
        }
        xs[x] = (b[x] - tmpSum) / tmp.elements[x][x]
    }
    return xs
}

/**
 * Mute matrix to get greater element at first position
 */
@OptIn(ExperimentalStdlibApi::class)
private fun MutableMatrix.mutateMatrixWithVector(vector: DoubleArray, start: Int): Boolean {
    var mutated = false
    var greatest: Int = start
    for (i in start..<elements.size) {
        if (elements[i][start].absoluteValue > elements[greatest][start].absoluteValue) {
            greatest = i
            mutated = true
        }
    }
    if (mutated) {
        elements[start] = elements[greatest].also { elements[greatest] = elements[start] }
        vector[start] = vector[greatest].also { vector[greatest] = vector[start] }
    }
    return mutated
}

