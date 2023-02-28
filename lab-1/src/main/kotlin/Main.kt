import kotlin.math.absoluteValue

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val a = mutableListOf<DoubleArray>(
        doubleArrayOf(10.0, -7.0, 0.0),
        doubleArrayOf(-3.0, 2.099, 6.0),
        doubleArrayOf(5.0, -1.0, 5.0),
    )
    val b = doubleArrayOf(7.0, 3.901, 6.0)

    val dim = a.size // matrix must be square unless we can slice square matrix from it
    assert(a.all { dim == it.size })
    assert(b.size == dim)


    val xs = DoubleArray(dim) {0.0}
    val tmp = a.copy().toMutableList()

    fun log(i: Int = -1, str: String = "") {
        println(">>>>>> $str $i  >>>>>>")
        println(tmp.pretty())
        println("### b:")
        println(b.pretty())
        println("### xs:")
        println(xs.pretty())
        println("<<<<<< $str $i  <<<<<<\n")
    }
    log()

    // Forward
    for (x in 0..<dim - 1) {
        // swap rows
        tmp.mutateMatrixWithVector(vector = b, start = x)
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

    println("###### Backward ######")

    //Backward
    for (x in xs.indices.reversed()) {
        var tmpSum: Double = 0.0
        for (i in x+1..xs.lastIndex) {
            tmpSum += tmp[x][i] * xs[i]
        }
        xs[x] = (b[x] - tmpSum)/tmp[x][x]
        log(x, "backward")
    }


}

/**
 * Mute matrix to get greater element at first position
 */
@OptIn(ExperimentalStdlibApi::class)
fun MutableList<DoubleArray>.mutateMatrixWithVector(vector: DoubleArray, start: Int) {
    var greatest: Int = start
    for (i in start..<this.size) {
        if (this[i][start].absoluteValue > this[greatest][start].absoluteValue) {
            greatest = i
        }
    }
    this[start] = this[greatest].also { this[greatest] = this[start] }
    vector[start] = vector[greatest].also { vector[greatest] = vector[start] }
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