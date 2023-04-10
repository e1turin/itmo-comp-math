package io.github.e1turin.model.domain.equation.nonlinear.method

import io.github.e1turin.model.domain.equation.SolvingMethod
import io.github.e1turin.model.util.derivative

class NewtonSolvingMethod(
    range: ClosedRange<Double>,
    private val function: (Double) -> Double,
    private val derivative: (Double) -> Double = function.derivative
) : SolvingMethod<Double> {

    init {
        require(function(range.start) * function(range.endInclusive) < 0) {
            "Function values must have opposite signs in bounds of range for \"Newton's solving method\""
        }
        require(derivative(range.start) * derivative(range.endInclusive) > 0) {
            "Function's 1st derivative non zero values must have same signs in a whole range for \"Newton's solving method\""
        }
        require(derivative.derivative(range.start) * derivative.derivative(range.endInclusive) > 0) {
            "Function's 2nd derivative non zero values must have same signs in a whole range for \"Newton's solving method\""
        }
    }

    override fun nextApproximation(current: Double): Double {
        val tan = derivative(current)

        check(tan != 0.0) {
            "Derivative must have hoh zero value on the whole range for \"Newton's solving method\""
        }

        val h = function(current) / tan
        val x = current - h
        return x
    }

    companion object {
        fun initialApproximation(
            range: ClosedRange<Double>,
            function: (Double) -> Double,
            firstDerivative: (Double) -> Double = function.derivative,
            secondDerivative: (Double) -> Double = firstDerivative.derivative
        ): Double {
            return if (function(range.start) * secondDerivative(range.start) > 0) range.start
            else if (function(range.endInclusive) * secondDerivative(range.endInclusive) > 0) range.endInclusive
            else throw IllegalArgumentException("Function value and it's 2nd derivative value must have same sign and not be zero at least in one bound of range")
        }
    }

}