package io.github.e1turin.output.view.features.pick_sample.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.output.view.entities.settings.model.DefaultSettings
import io.github.e1turin.output.view.entities.settings.model.EquationSettings
import io.github.e1turin.output.view.entities.settings.model.Settings
import io.github.e1turin.output.view.entities.settings.model.SystemSettings
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun SamplePicker(
    modifier: Modifier = Modifier,
    model: Settings
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (model) {
            is EquationSettings -> EquationPicker(settings = model)
            is SystemSettings -> SystemPicker(settings = model)
            is DefaultSettings -> Box(
                contentAlignment = Alignment.Center
            ) {
                Text("Select a task first")
            }
        }
    }
}

@Composable
private fun EquationPicker(
    modifier: Modifier = Modifier,
    settings: EquationSettings
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Selection(description = "x^4/10 - 2 = 0") {
            settings.onEquationSelect { x ->
                x.pow(4.0) / 10 - 2
            }
            settings.onApproximationFunctionSelect {
                (20.0).pow(1.0 / 4)
            }
            settings.onDerivativeFunctionSelect { x ->
                4 * x.pow(3.0) / 10
            }
        }
            .also { Spacer(Modifier.size(10.dp)) }

        Selection(description = "ln(x) = 0") {
            settings.onEquationSelect { x ->
                log(x, kotlin.math.E)
            }
            settings.onApproximationFunctionSelect { x ->
                1.0
            }
            settings.onDerivativeFunctionSelect { x ->
                1 / x
            }
        }
            .also { Spacer(Modifier.size(10.dp)) }

        Selection(description = "[1] x^3 - x + 4 = 0") {
            settings.onEquationSelect { x ->
                x.pow(3.0) - x + 4
            }
            settings.onApproximationFunctionSelect { x ->
                x.pow(3.0) + 4
            }
            settings.onDerivativeFunctionSelect { x ->
                3 * x.pow(2.0) - 1
            }
        }
            .also { Spacer(Modifier.size(5.dp)) }

        Selection(description = "[2] x^3 - x + 4 = 0") {
            settings.onEquationSelect { x ->
                x.pow(3.0) - x + 4
            }
            settings.onApproximationFunctionSelect { x ->
                (x - 4).pow(1.0 / 3)
            }
            settings.onDerivativeFunctionSelect { x ->
                (x - 4).pow(-2.0 / 3) / 3
            }
        }
    }
}

@Composable
private fun SystemPicker(
    modifier: Modifier = Modifier,
    settings: SystemSettings
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Selection("{ (x-1)^2 + y^2 - 4 = 0,\n  (x+1)^2 + y^2 - 4 = 0 }") {
            settings.apply {
                onSystemSelect(
                    listOf(
                        { x -> (x[0] - 1).pow(2) + x[1].pow(2) - 4 },
                        { x -> (x[0] + 1).pow(2) + x[1].pow(2) - 4 },
                    )
                )
                onJacobianSelect(
                    listOf(
                        listOf({ x -> 2 * (x[0] - 1) }, { x -> 2 * x[1] }),
                        listOf({ x -> 2 * (x[0] + 1) }, { x -> 2 * x[1] })
                    )
                )
                onApproximationFunctionsSelect(
                    listOf(
                        { x -> sqrt(4 - x[1].pow(2)) + 1 },
                        { x -> sqrt(4 - (x[0] + 1).pow(2)) },
                    )
                )
            }

        }
            .also { Spacer(Modifier.size(10.dp)) }

        Selection("{ 0.1x² + x + 0.2y² - 0.3,\n  0.2x² + y + 0.1xy - 0.7 }") {
            settings.apply {
                onSystemSelect(
                    listOf(
                        { x -> 0.1 * x[0].pow(2) + x[0] + 0.2 * x[1].pow(2) - 0.3 },
                        { x -> 0.2 * x[0].pow(2) + x[1] + 0.1 * x[0] * x[1] - 0.7 }
                    )
                )
                onJacobianSelect(
                    listOf(
                        listOf({ x -> -0.2 * x[0] /*        */ }, { x -> -0.4 * x[1] }),
                        listOf({ x -> -0.4 * x[0] - 0.1 * x[1] }, { x -> -0.1 * x[0] })
                    )
                )
                onApproximationFunctionsSelect(
                    listOf(
                        { x -> 0.3 - 0.1 * x[0].pow(2) - 0.2 * x[1].pow(2) },
                        { x -> 0.7 - 0.2 * x[0].pow(2) - 0.1 * x[0] * x[1] },
                    )
                )
            }
        }
    }
}


@Composable
private fun Selection(description: String, onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable(onClick = onClick)
            .border(1.dp, Color.Black)
            .padding(10.dp),
        text = description,
        fontSize = 20.sp,
    )
}