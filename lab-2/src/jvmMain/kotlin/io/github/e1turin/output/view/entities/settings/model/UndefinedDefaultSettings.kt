package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

object UndefinedDefaultSettings : DefaultSettings {
    override val data: Value<Data> = MutableValue(Data)
    override val isCompleted: Value<Boolean> = MutableValue(false)

    object Data : Settings.Data()
}