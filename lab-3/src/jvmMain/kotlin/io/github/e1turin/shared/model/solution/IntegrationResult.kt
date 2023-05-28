package io.github.e1turin.shared.model.solution

data class IntegrationResult(
    val area: Double,
    val precision: Double,
    val divisions: Int,
    val convergence: Boolean
)
