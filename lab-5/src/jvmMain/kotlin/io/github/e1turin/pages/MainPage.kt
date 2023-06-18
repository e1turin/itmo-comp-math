package io.github.e1turin.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import io.github.e1turin.shared.config.compose.TM
import io.github.e1turin.widgets.finiteDifferences.output.FiniteDifferencesOutput
import io.github.e1turin.widgets.graph.output.GraphOutput
import io.github.e1turin.widgets.points.input.PointsInput
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun MainPage(
    model: MainActivity,
    modifier: Modifier = Modifier,
) {
    val message by model.message

    Row(modifier = modifier) {
        Column {
            if (message.isNotBlank()) {
                Text(message, color = Color.Black, fontSize = 20.0.sp, modifier = Modifier.background(Color.Red))
                LaunchedEffect(message) {
                    delay(5.0.seconds).also { model.hideErrorMessage() }
                }
            }
            PointsInput(model, TM.weight(1F))
        }
        GraphOutput(model, TM.weight(1F))
        FiniteDifferencesOutput(model, TM)
    }
}