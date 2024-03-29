package io.github.e1turin.output.view.features.select_task.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.e1turin.output.view.entities.task.model.Task
import io.github.e1turin.output.view.entities.task.model.TaskType
import io.github.e1turin.output.view.shared.lib.compose.Random

@Composable
fun TaskSelection(
    modifier: Modifier = Modifier,
    model: Task,
) {
    val taskType by model.taskType.subscribeAsState()

    Box(modifier = modifier) {
        Row(
            Modifier.fillMaxWidth()
        ) {
            Column(Modifier.width(IntrinsicSize.Max)) {
                SelectOption(
                    "Equation", taskType is TaskType.Equation
                ) {
//                    if (data.taskType !is TaskType.Equation)
                    model.onSelectOther(TaskType.Equation.Undefined)
                }

                SelectOption(
                    "System of Equations", taskType is TaskType.System
                ) {
//                    if (data.taskType !is TaskType.EquationSystem)
                    model.onSelectOther(TaskType.System.Undefined)
                }
            }

            Column(Modifier.width(IntrinsicSize.Max)) {
                when (val type = taskType) {
                    is TaskType.Equation -> {
                        SelectOption(
                            "Chord Method", type == TaskType.Equation.Chord
                        ) { model.onSelectOther(TaskType.Equation.Chord) }

                        SelectOption(
                            "Newton Method", type == TaskType.Equation.Newton
                        ) { model.onSelectOther(TaskType.Equation.Newton) }

                        SelectOption(
                            "Simple Iteration Method", type == TaskType.Equation.SimpleIteration
                        ) { model.onSelectOther(TaskType.Equation.SimpleIteration) }
                    }

                    is TaskType.System -> {
                        SelectOption(
                            "Simple Iteration Method", type == TaskType.System.SimpleIteration
                        ) { model.onSelectOther(TaskType.System.SimpleIteration) }
                    }

                    is TaskType.Undefined -> {}
                }
            }
        }
    }
}

@Composable
fun SelectOption(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.Random, RoundedCornerShape(10.dp))
            .clickable(onClick = onSelect)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = {})
        Text(text, modifier = Modifier.padding(20.dp))
    }
}