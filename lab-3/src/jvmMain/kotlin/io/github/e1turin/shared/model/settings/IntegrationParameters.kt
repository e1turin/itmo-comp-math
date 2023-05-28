package io.github.e1turin.shared.model.settings

import io.github.e1turin.entities.integrator.model.IntegrationMethod
import io.github.e1turin.shared.config.functionWithLabelStore

data class IntegrationParameters(
    val functionLabel: String = functionWithLabelStore.keys.first(),
    val method: IntegrationMethod = IntegrationMethod.LeftRectangle,
    val divisions: Int = 4,
    val range: ClosedFloatingPointRange<Double> = -1.0..1.0,
    val precision: Double = 0.01
)
