package io.github.e1turin.output.view.pages.main.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.graph.ui.Plot
import io.github.e1turin.output.view.entities.settings.ui.SamplePicker
import io.github.e1turin.output.view.entities.settings.ui.SettingsPanel
import io.github.e1turin.output.view.entities.task.ui.TaskSelection
import io.github.e1turin.output.view.pages.main.model.MainPage

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    model: MainPage
) {
    val settings by model.task.settings.subscribeAsState()
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
                    if (isReady && settings.isCompleted) {
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
                    modifier = Modifier.weight(1F)
                ) {
                    SettingsPanel(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F), model = settings.also { println("[ui/MainPageUI.kt]${it}") }
                    )
                        .also { Spacer(Modifier.size(5.dp)) }

                    Button(
                        modifier = Modifier
                            .wrapContentSize()
//                            .weight(1F)
                        ,
                        onClick = { isReady = true }) {
                        Text("Ready")
                    }
                }
            }
        }
    }
}