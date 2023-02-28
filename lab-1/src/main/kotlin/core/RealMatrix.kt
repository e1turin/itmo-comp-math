package core

import Exceptions.MatrixDimensionsMismatchException

@OptIn(ExperimentalStdlibApi::class)
open class RealMatrix(protected val elems: MutableList<MutableList<Double>>) {
    public val rowNum: Int get() = elems.size
    val colNum: Int get() = elems[0].size

    /**
     * core.Matrix get operator receives 2 indices: row number and column number. Negative values stands for offset from the
     * and of array like in python.
     * @param m ceil number of row starts from 0
     * @param n ceil number of column starts from 0
     * @return element from specified index
     */
    operator fun get(m: Int): MutableList<Double> {
        return elems[m]
    }

    operator fun set(m: Int, value: MutableList<Double>) {
        elems[m] = value
    }

    operator fun get(m: Int, n: Int): Double {
        return if (m > 0) {
            if (n > 0) {
                elems[m][n]
            } else {
                elems[m][colNum + n]
            }
        } else {
            if (n > 0) {
                elems[rowNum + m][n]
            } else {
                elems[rowNum + m][colNum + n]
            }
        }
    }

    /**
     * core.Matrix set operator receives 2 indices: row number and column number of element in which should be inserted `value`.
     * Negative values of `m` and `n` stands for offset from the and of array like in python.
     * @param m ceil number of row starts from 0
     * @param n ceil number of column starts from 0
     * @return element from specified index
     */
    operator fun set(m: Int, n: Int, value: Double) {
        if (m > 0) {
            if (n > 0) {
                elems[m][n] = value
            } else {
                elems[m][colNum + n] = value
            }
        } else {
            if (n > 0) {
                elems[rowNum + m][n] = value
            } else {
                elems[rowNum + m][colNum + n] = value
            }
        }
    }

    override fun toString(): String {
        return elems.joinToString(
            prefix = "[", postfix = "]", separator = ",\n "
        ) { row ->
            row.joinToString(
                prefix = "[", postfix = "]", separator = ",\t"
            ) { el -> el.toString() }
        }
    }

    fun swapRows(indices: Pair<Int, Int>) {
        val tmpRow = elems[indices.first]
        elems[indices.first] = elems[indices.second]
        elems[indices.second] = tmpRow
    }

    @Throws(MatrixDimensionsMismatchException::class)
    fun extend(matrix: RealMatrix): RealMatrix {
        if (this.rowNum != matrix.rowNum) throw MatrixDimensionsMismatchException(
            "core.Matrix which is tried to extend has $rowNum×$colNum dimensions," +
                    " but extension matrix has ${matrix.rowNum}×${matrix.colNum} dimensions."
        )
        val tmpMatrix = MutableList<MutableList<Double>>(matrix.rowNum) { mutableListOf() }
        for(i in 0..< this.rowNum){
            tmpMatrix[i] = this[i].plus(matrix[i]).toMutableList()
        }
        return RealMatrix(tmpMatrix)
    }

}
