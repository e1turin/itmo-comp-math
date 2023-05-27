package io.github.e1turin.entities.integrator.model

import io.github.e1turin.shared.config.functionStore
import io.github.e1turin.shared.lib.length
import io.github.e1turin.shared.model.settings.IntegrationParameters
import io.github.e1turin.shared.model.solution.IntegrationResult

class LeftRectangleIntegrator(private val settings: IntegrationParameters) : Integrator {

    init {
        check(settings.range.length > 0) { "Inspected range mustn't be empty. Check its bounds." }
        check(settings.functionId >= 0) { "Function ID mustn't be negative." }
    }

    override fun integrate(): IntegrationResult {
        val step = settings.range.length / settings.divisions

        // TODO("STUB Integration method")
        var integralSum = 0.0
        val currentParam = settings.range.start
        val function: (Double) -> Double = functionStore[settings.functionId]

        repeat(settings.divisions) {
            integralSum += function(currentParam) * step
        }

        return IntegrationResult(
            area = integralSum,
            precision = 0.0,
            divisions = settings.divisions,
            convergence = false
        )
    }
}