package core

import Exceptions.MatrixDimensionsMismatchException

@OptIn(ExperimentalStdlibApi::class)
abstract class Matrix<T>(dims: Dimensions, elements: List<List<T>>) {
    open val elements: List<List<T>> = elements
    val rowNum: Int = dims.m
    val colNum: Int = dims.n
    init {
        @Throws(MatrixDimensionsMismatchException::class)
        for (i in 0..<rowNum) {
            if (elements[i].size != colNum)
                throw MatrixDimensionsMismatchException(
                    "Matrix has wrong dimensions: row $i has size ${elements[i].size} while matrix has $colNum columns"
                )
        }
    }

    @Throws(IndexOutOfBoundsException::class)
    operator fun get(m: Int): List<T> {
        return if (m > 0)
            elements[m]
        else
            elements[rowNum + m]
    }

    data class Dimensions(val m: Int, val n: Int) {
        init {
            require(m > 0) {"dimension of matrix must be positive but m is not"}
            require(n > 0) {"dimension of matrix must be positive but n is not"}
        }
    }
}

infix fun Int.x(other: Int): Matrix.Dimensions {
    return Matrix.Dimensions(this, other)
}