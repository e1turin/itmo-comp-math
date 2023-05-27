package io.github.e1turin.entities.settings.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import io.github.e1turin.entities.integrator.model.IntegrationSettings
import io.github.e1turin.shared.lib.mutate

object SettingsHolder {
    private val _props = mutableStateOf(Settings())
    val props: State<Settings> = _props

    fun onDivisionsChange(divisions: Int) {
        _props.mutate { copy(divisions = divisions) }
    }

    fun onRangeChange(range: ClosedFloatingPointRange<Double>) {
        _props.mutate { copy(range = range) }
    }

    fun onRangeStartChange(start: Double) {
        _props.mutate { copy(range = start..range.endInclusive) }
    }

    fun onRangeEndChange(endInclusive: Double) {
        _props.mutate { copy(range = range.start..endInclusive) }
    }

    fun onAccuracyChange(accuracy: Double) {
        _props.mutate { copy(accuracy = accuracy) }
    }

    data class Settings(
        override val divisions: Int = 4,
        override val range: ClosedFloatingPointRange<Double> = -1.0..1.0,
        override val accuracy: Double = 0.01
    ) : IntegrationSettings
}
