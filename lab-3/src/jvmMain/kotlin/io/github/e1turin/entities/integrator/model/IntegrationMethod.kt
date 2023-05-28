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
    Trapezoid;

    override fun toString(): String {
        return when(this) {
            LeftRectangle -> "Rectangle Left"
            RightRectangle -> "Rectangle Right"
            CenterRectangle -> "Rectangle Center"
            Simpson -> "Simpson's"
            Trapezoid -> "Trapezoid"
        }
    }
}
