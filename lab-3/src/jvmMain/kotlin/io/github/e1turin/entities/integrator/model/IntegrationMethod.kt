package io.github.e1turin.entities.integrator.model

import io.github.e1turin.shared.model.solution.IntegrationResult

interface Integrator {
    fun integrate(): IntegrationResult
}

enum class IntegrationMethod {
    LeftRectangle,
    RightRectangle,
    CenterRectangle,
    Simpson,
    Trapezoid
}
