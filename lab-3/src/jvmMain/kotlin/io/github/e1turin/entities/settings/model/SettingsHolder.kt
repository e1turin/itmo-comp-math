package io.github.e1turin.entities.settings.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import io.github.e1turin.entities.integrator.model.IntegrationMethod
import io.github.e1turin.shared.lib.mutate
import io.github.e1turin.shared.model.settings.IntegrationParameters

object SettingsHolder {
    private val _params = mutableStateOf(IntegrationParameters())
    val params: State<IntegrationParameters> = _params

    fun onDivisionsChange(divisions: Int) {
        _params.mutate { copy(divisions = divisions) }
    }

    fun onRangeChange(range: ClosedFloatingPointRange<Double>) {
        _params.mutate { copy(range = range) }
    }

    fun onRangeStartChange(start: Double) {
        _params.mutate { copy(range = start..range.endInclusive) }
    }

    fun onRangeEndChange(endInclusive: Double) {
        _params.mutate { copy(range = range.start..endInclusive) }
    }

    fun onPrecisionChange(accuracy: Double) {
        _params.mutate { copy(precision = accuracy) }
    }

    fun onMethodSelect(method: IntegrationMethod) {
        _params.mutate { copy(method = method) }
    }

    fun onFunctionSelect(functionLabel: String) {
        _params.mutate { copy(functionLabel = functionLabel) }
    }
}

