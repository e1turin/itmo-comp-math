package io.github.e1turin.entities.integrator.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import io.github.e1turin.shared.model.solution.IntegrationResult

object IntegrationResultHolder {
    private var _data = mutableStateOf<IntegrationResult?>(null)
    val data: State<IntegrationResult?> = _data

    fun onNewResult(data: IntegrationResult) {
        _data.value = data
    }

    fun clearData() {
        _data.value = null
    }
}