package io.github.e1turin.output.view.pages.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.features.draw_plot.ui.Plot
import io.github.e1turin.output.view.features.pick_sample.ui.SamplePicker
import io.github.e1turin.output.view.features.select_settings.ui.SettingsPanel
import io.github.e1turin.output.view.features.select_task.ui.TaskSelection
import io.github.e1turin.output.view.features.present_solution.ui.SolutionPanel
import io.github.e1turin.output.view.pages.main.model.MainPage

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    model: MainPage
) {
    val settings by model.task.settings.subscribeAsState()
    val isCompleted by settings.isCompleted.subscribeAsState()
    var isReady by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TaskSelection(
                modifier = Modifier.fillMaxWidth(),
                model = model.task
            )
            Row {
                Box(
                    modifier = Modifier.weight(1F),
                ) {
                    if (isReady && isCompleted) {
                        Plot(
                            modifier = Modifier
                                .fillMaxSize()
                            ,
                            model = settings
                        )
                    } else {
                        isReady = false
                        SamplePicker(
                            modifier = Modifier.fillMaxSize(),
                            model = settings
                        )
                    }
                }
                    .also { Spacer(Modifier.size(20.dp)) }

                Column(
                    modifier = Modifier.width(500.dp).padding(10.dp)
                ) {
                    SettingsPanel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                        ,
                        model = settings.also { println("[ui/MainPageUI.kt]${it}") }
                    )
                        .also { Spacer(Modifier.size(5.dp)) }

                    if (isReady && isCompleted)
                        SolutionPanel(
                            modifier = Modifier
                                .background(Color.LightGray, RoundedCornerShape(10.dp))
                                .height(200.dp)
                                .fillMaxWidth()
                                .padding(10.dp),
                            settings = settings
                        )
                            .also { Spacer(Modifier.size(5.dp)) }

                    Button(
                        modifier = Modifier
                            .wrapContentSize()
                            .fillMaxWidth(),
                        enabled = isCompleted,
                        onClick = { isReady = true }) {
                        Text("Ready")
                    }
                }
            }
        }
    }
}