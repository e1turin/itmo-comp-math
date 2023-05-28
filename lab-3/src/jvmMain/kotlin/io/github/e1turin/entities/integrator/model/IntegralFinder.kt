package io.github.e1turin.entities.integrator.model

import androidx.compose.runtime.getValue
import io.github.e1turin.entities.settings.model.SettingsHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object IntegralFinder {
    private val scope = CoroutineScope(Dispatchers.Default)
    fun integrate() {
        val settings by SettingsHolder.params
        scope.launch {
            try {
                val integrator = when (settings.method) {
                    IntegrationMethod.LeftRectangle -> LeftRectangleIntegrator(settings)
                    IntegrationMethod.RightRectangle -> RightRectangleIntegrator(settings)
                    IntegrationMethod.CenterRectangle -> CenterRectangleIntegrator(settings)
                    IntegrationMethod.Simpson -> SimpsonIntegrator(settings)
                    IntegrationMethod.Trapezoid -> TrapezoidIntegrator(settings)
                }

                val result = integrator.integrate()

                IntegrationResultHolder.onNewResult(result)
            } catch (e: Throwable) {
                println("[IntegralFinder.kt]error: $e")
            }
        }

    }
}