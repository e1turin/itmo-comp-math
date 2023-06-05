package io.github.e1turin.model.matrix

interface Matrix {
    val elements: Array<DoubleArray>
}

inline fun Matrix.clone(): Matrix = Array(elements.size) { elements[it].clone() }.toMatrix()

inline fun Array<DoubleArray>.toMatrix() = object : Matrix {
    override val elements: Array<DoubleArray> = this@toMatrix
}