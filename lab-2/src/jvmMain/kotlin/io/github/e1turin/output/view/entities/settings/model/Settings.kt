package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.Value

sealed interface Settings {
    val data: Value<Data>
    val isCompleted: Boolean

    abstract class Data
}
