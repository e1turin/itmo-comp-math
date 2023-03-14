package core
inline fun List<DoubleArray>.copy() = this.map { it.clone() }

inline fun List<DoubleArray>.toMatrix() = object : Matrix {
    override val elements: List<DoubleArray> = this@toMatrix
}

inline fun List<DoubleArray>.toMutableMatrix() = object : MutableMatrix {
    override var elements: MutableList<DoubleArray> = this@toMutableMatrix.toMutableList()
}
