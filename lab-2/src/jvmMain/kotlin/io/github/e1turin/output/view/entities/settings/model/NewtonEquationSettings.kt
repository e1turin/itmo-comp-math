package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.output.view.entities.settings.model.Settings.Translation
import io.github.e1turin.output.view.shared.lib.decompose.mutate


class NewtonEquationSettings : EquationSettings {
    private val _data = MutableValue(
        NewtonData(
            range = -3.0..3.0,
            initialValue = 0.0
        )
    )
    override val data: Value<NewtonData> = _data

    val _isComplete = MutableValue(false)
    override val isCompleted: Value<Boolean> = _isComplete

    override fun onEquationSelect(function: (Double) -> Double): Unit =
        _data.mutate { copy(function = function) }.also { _isComplete.mutate { true } }

    fun onRangeChange(range: ClosedRange<Double>): Unit =
        _data.mutate { copy(range = range) }

    fun onInitialValueChange(initialValue: Double): Unit =
        _data.mutate { copy(initialValue = initialValue) }

    fun onScaleValueChange(scale: Double): Unit =
        _data.mutate { copy(scale = scale) }

    fun onTranslateXChange(offsetX: Double): Unit =
        _data.mutate { copy(translate = translate.copy(offsetX = offsetX)) }

    fun onTranslateYChange(offsetY: Double): Unit =
        _data.mutate { copy(translate = translate.copy(offsetY = offsetY)) }

    fun onTranslateFullChange(offset: Translation): Unit =
        _data.mutate { copy(translate = offset) }

    data class NewtonData(
        val function: ((Double) -> Double)? = null,
        val range: ClosedRange<Double>,
        val initialValue: Double,
        val scale: Double = 1.0,
        val translate: Translation = Translation(0.0, 0.0)
    ) : Settings.Data()
}