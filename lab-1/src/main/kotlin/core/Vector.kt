package core

open class Vector(content: MutableList<Double>): RealMatrix(content.map { mutableListOf(it) }.toMutableList())