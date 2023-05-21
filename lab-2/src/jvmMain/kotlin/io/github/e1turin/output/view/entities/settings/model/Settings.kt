package io.github.e1turin.output.view.entities.settings.model

import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

sealed interface Settings {
    val data: Value<Data>
    val isCompleted: Value<Boolean>

    @Serializable
    sealed class Data
}
