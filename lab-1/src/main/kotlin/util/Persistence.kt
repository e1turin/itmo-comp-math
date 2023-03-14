package util
inline fun List<DoubleArray>.copy() = this.map { it.clone() }
