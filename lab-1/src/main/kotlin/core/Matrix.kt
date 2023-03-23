package core
interface Matrix{
    val elements: List<DoubleArray>
}

interface MutableMatrix : Matrix{
    override var elements: MutableList<DoubleArray>
}

