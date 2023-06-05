package io.github.e1turin.model.matrix

import kotlin.math.absoluteValue
import kotlin.math.pow


fun Double.approx(double: Double): Boolean {
    return (this - double).absoluteValue < 1e-6
}

/**
 * extension function on matrix to solve system of linear equations described by this [Matrix] and vector [b].
 * @return array of double with size of given [b] (equal to matrix amount of rows) if everything is ok.
 * @return null if system can not be solved because of zero value of matrix determinant.
 * @throws IllegalArgumentException on matrix and vector dimensions mismatch
 */
fun Matrix.solveSLE(b: DoubleArray): DoubleArray? {
    val dim = elements.size // matrix must be square unless we can slice square matrix from it

    require(elements.all { dim == it.size }) { "Matrix dimensions mismatch (not square matrix)" }
    require(b.size == dim) { "Matrix and vector dimensions mismatch (must have same number of rows)" }

    val xs = DoubleArray(dim) { 0.0 }
    val tmp = this.clone()

    var countRowSwaps = 0

    // Forward
    for (x in 0 until dim - 1) {
        if (tmp.mutateMatrixWithVector(vector = b, start = x)) {
            countRowSwaps++
        }

        for (nextRow in x + 1 until dim) {
            val mul = -tmp.elements[nextRow][x] / tmp.elements[x][x]
            for (i in 0 until dim) {
                tmp.elements[nextRow][i] += tmp.elements[x][i] * mul
            }
            b[nextRow] += b[x] * mul

            tmp.elements[nextRow][x] = 0.0 // must be 0 because of math
        }
    }

    val det: Double = (-1.0).pow(countRowSwaps % 2) * tmp.elements.foldIndexed(1.0) { ii, acc, a -> acc * a[ii] }

    if (det.approx(0.0)) {
        return null
    }

    // Backward
    for (x in xs.indices.reversed()) {
        var tmpSum = 0.0
        for (i in x + 1..xs.lastIndex) {
            tmpSum += tmp.elements[x][i] * xs[i]
        }
        xs[x] = (b[x] - tmpSum) / tmp.elements[x][x]
    }

    return xs
}

/**
 * Method for calculating vector of deviance ('r')
 * @param [vector] is vector of right-hand part of matrix with dimension 'n'
 * @param [solution] is vector of parameters with dimension 'n'
 */
fun Matrix.calculateDeviance(vector: DoubleArray, solution: DoubleArray) = DoubleArray(vector.size) { i ->
    vector[i] - elements[i].foldIndexed(0.0) { j, acc, aij ->
        acc + aij * solution[j]
    }
}

/**
 * Mute matrix to get greater element at first position
 */
private fun Matrix.mutateMatrixWithVector(vector: DoubleArray, start: Int): Boolean {
    var mutated = false
    var greatest: Int = start
    for (i in start until elements.size) {
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
