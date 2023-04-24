package io.github.e1turin.output.view.pages.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.output.view.entities.form.model.Settings
import io.github.e1turin.output.view.entities.form.ui.SettingsPanel

sealed interface Task {
    data class Equation(val method: EquationSolver?) : Task
    data class System(val method: SystemSolver?) : Task
    object None : Task

    sealed interface EquationSolver {
        object Chord : EquationSolver
        object Newton : EquationSolver
        object SimpleIteration : EquationSolver
    }

    sealed interface SystemSolver {
        object SimpleIteration : SystemSolver
    }
}

@Composable
fun MainActivity() {
    var task by remember { mutableStateOf<Task>(Task.None) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.Cyan), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                val onClick = { task = Task.Equation(null) }
                RadioButton(
                    selected = task is Task.Equation,
                    onClick = onClick,
                )
                Spacer(Modifier.size(16.dp))
                Text("Equation solver", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
            }
            Spacer(Modifier.size(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val onClick = { task = Task.System(null) }
                RadioButton(
                    selected = task is Task.System,
                    onClick = onClick,
                )
                Spacer(Modifier.size(16.dp))
                Text("System solver", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
            }
        }

        Spacer(Modifier.size(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.LightGray),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            when (task) {
                is Task.Equation -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.Equation(Task.EquationSolver.Chord) }
                        RadioButton(
                            selected = task == Task.Equation(Task.EquationSolver.Chord),
                            onClick = onClick,
                        )
                        Spacer(Modifier.size(16.dp))
                        Text("Chord method", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
                    }

                    Spacer(Modifier.size(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.Equation(Task.EquationSolver.Newton) }
                        RadioButton(
                            selected = task == Task.Equation(Task.EquationSolver.Newton),
                            onClick = { task = Task.Equation(Task.EquationSolver.Newton) },
                        )
                        Spacer(Modifier.size(16.dp))
                        Text("Newton method", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
                    }

                    Spacer(Modifier.size(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.Equation(Task.EquationSolver.SimpleIteration) }
                        RadioButton(
                            selected = task == Task.Equation(Task.EquationSolver.SimpleIteration),
                            onClick = onClick,
                        )
                        Spacer(Modifier.size(16.dp))
                        Text(
                            "Simple Iteration method",
                            fontSize = 18.sp,
                            modifier = Modifier.clickable(onClick = onClick)
                        )
                    }
                }

                is Task.System -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.System(Task.SystemSolver.SimpleIteration) }
                        RadioButton(
                            selected = task == Task.System(Task.SystemSolver.SimpleIteration),
                            onClick = onClick,
                        )
                        Spacer(Modifier.size(16.dp))
                        Text(
                            "Simple Iteration method",
                            fontSize = 18.sp,
                            modifier = Modifier.clickable(onClick = onClick)
                        )
                    }
                }

                is Task.None -> {
                    Box(Modifier.wrapContentSize()) {
                        Text("Select task before")
                    }
                }
            }
        }

        Spacer(Modifier.size(40.dp))

        Row {
            Box(Modifier.size(600.dp).weight(2f), contentAlignment = Alignment.Center) {
                when (task) {
                    is Task.Equation -> {
                        ListEquations()
                    }

                    is Task.System -> {
                        ListSystems()
                    }

                    is Task.None -> {
                        Text("Select task first")
                    }
                }
            }
            Spacer(Modifier.size(20.dp))
            Box(Modifier.weight(1f)) {
                SettingsPanel(Settings()) {}//TODO
            }
        }

    }
}

@Composable
fun ListEquations() {
    Column {
        Text(modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { }, text = "x² + 2x + 1 = 0")
        Spacer(Modifier.size(10.dp))
        Text(modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { }, text = "x² + 2x + 1 = 0")
        Spacer(Modifier.size(10.dp))
        Text(modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { }, text = "x² + 2x + 1 = 0")
    }
}

@Composable
fun ListSystems() {
    Column {
        Text(modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { }, text = "{x² + 2x + 1 = 0, x² + 2x + 1 = 0}")
        Spacer(Modifier.size(10.dp))
        Text(modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { }, text = "{x² + 2x + 1 = 0, x² + 2x + 1 = 0}")
    }
}
