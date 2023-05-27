package io.github.e1turin.entities.integrator.model

interface IntegrationSettings {
    val divisions: Int
    val range: ClosedFloatingPointRange<Double>
    val accuracy: Double
}