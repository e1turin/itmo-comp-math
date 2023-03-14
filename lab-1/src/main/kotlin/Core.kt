import util.copy
import util.debug
import util.pretty
import util.printSep
import kotlin.math.absoluteValue

fun Double.approx(double: Double): Boolean {
    return (this - double).absoluteValue < 1e-6
}

/**
 * extension function on matrix that stands for elements of system of linear equations.
 */
@OptIn(ExperimentalStdlibApi::class)
fun List<DoubleArray>.solveSLE(b: DoubleArray, logMiddleResults: Boolean = false): DoubleArray? {
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
        det *= tmp[i][i]
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

