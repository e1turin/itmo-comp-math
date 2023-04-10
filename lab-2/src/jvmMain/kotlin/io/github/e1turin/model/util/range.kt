package io.github.e1turin.model.util

val ClosedRange<Double>.length: Double get() = endInclusive - start

infix fun ClosedRange<Double>.step(step: Double): Iterator<Double> =
    object : Iterator<Double> {
        var current: Double = this@step.start

        override fun hasNext(): Boolean = current + step <= this@step.endInclusive

        override fun next(): Double {
            current += step
            return current
        }
    }
