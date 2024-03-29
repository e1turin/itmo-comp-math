package io.github.e1turin.entities.integrator.model

import io.github.e1turin.shared.config.functionWithLabelStore
import io.github.e1turin.shared.config.maxNIteration
import io.github.e1turin.shared.lib.length
import io.github.e1turin.shared.model.settings.IntegrationParameters
import io.github.e1turin.shared.model.solution.IntegrationResult
import kotlin.math.abs
import kotlin.math.pow

class RightRectangleIntegrator(private val settings: IntegrationParameters) : Integrator {

    init {
        check(settings.range.length > 0) { "Inspected range mustn't be empty. Check its bounds." }
        check(functionWithLabelStore.keys.contains(settings.functionLabel)) { "Function must be in defined set." }
    }

    override fun integrate(): IntegrationResult {
        val function: (Double) -> Double = functionWithLabelStore[settings.functionLabel]!!

        var currentIntegrationSum = 0.0
        var currentDivisions = settings.divisions
        var deviance: Double = Double.MAX_VALUE

        for(iteration in 0..maxNIteration) {
            val integralSum = nextIntegralApproximation(
                start = settings.range.start,
                endInclusive = settings.range.endInclusive,
                divisions = currentDivisions,
                function = function
            )

            val preciseIntegralSum = nextIntegralApproximation(
                start = settings.range.start,
                endInclusive = settings.range.endInclusive,
                divisions = currentDivisions * 2,
                function = function
            )

            deviance = abs(integralSum - preciseIntegralSum) / (2.0.pow(2) - 1)
            currentIntegrationSum = preciseIntegralSum
            currentDivisions *= 2

            if (deviance < settings.precision ) break
        }


        return IntegrationResult(
            area = currentIntegrationSum,
            precision = deviance,
            divisions = currentDivisions,
            convergence = false
        )
    }

    private fun nextIntegralApproximation(
        start: Double,
        endInclusive: Double,
        divisions: Int,
        function: (Double) -> Double
    ): Double {
        val step = (endInclusive - start) / divisions

        var integralSum = 0.0

        var currentParam = start + step

        repeat(divisions) {
            integralSum += function(currentParam) * step
            currentParam += step
        }

        return integralSum
    }
}