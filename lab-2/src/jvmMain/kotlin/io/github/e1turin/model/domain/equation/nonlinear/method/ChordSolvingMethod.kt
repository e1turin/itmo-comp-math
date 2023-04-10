package io.github.e1turin.model.domain.equation.nonlinear.method

import io.github.e1turin.model.domain.equation.SolvingMethod


class ChordSolvingMethod(
    val function: (Double) -> Double
) : SolvingMethod<ClosedRange<Double>> {

    override fun nextApproximation(current: ClosedRange<Double>): ClosedRange<Double> {
        val startValue = function(current.start)
        val endValue = function(current.endInclusive)
        require(startValue * endValue < 0) {
            "Function values must have opposite signs in bounds of range for \"Chord solving method\""
        }

        val middle = current.start - (current.endInclusive - current.start) * startValue / (endValue - startValue)
        val middleValue: Double = function(middle)

        return if (startValue * middleValue < 0) {
            current.start..middle
        } else if (endValue * middleValue < 0) {
            middle..current.endInclusive
        } else {
            middle..middle
        }
    }

}