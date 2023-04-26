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

sealed interface Task {
    sealed interface EquationSolver : Task {
        data class Chord(
            val initialRange: ClosedRange<Double> = 0.0..0.0,
        ) : EquationSolver

        data class Newton(
            val initialValue: Double? = null,
        ) : EquationSolver

        data class SimpleIteration(
            val initialValue: Double? = null,
        ) : EquationSolver

        object Undefined : EquationSolver
    }

    sealed interface SystemSolver : Task {
        data class SimpleIteration(
            val initialValue: List<Double>? = null,
        ) : SystemSolver

        object Undefined : SystemSolver
    }

    object Undefined : Task
}

@Composable
fun MainActivityTest() {
    var task by remember { mutableStateOf<Task>(Task.Undefined) }
    var settings by remember { mutableStateMapOf<String, Any>() }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //Task selection
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.Cyan), horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val onClick = { task = Task.EquationSolver.Undefined }
                RadioButton(
                    selected = task is Task.EquationSolver,
                    onClick = onClick,
                )
                Spacer(Modifier.size(16.dp))
                Text("Equation solver", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
            }
            Spacer(Modifier.size(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val onClick = { task = Task.SystemSolver.Undefined }
                RadioButton(
                    selected = task is Task.SystemSolver,
                    onClick = onClick,
                )
                Spacer(Modifier.size(16.dp))
                Text("System solver", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
            }
        }

        Spacer(Modifier.size(40.dp))
        //Task method selection
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.LightGray),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            when (task) {
                is Task.EquationSolver -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.EquationSolver.Chord() }
                        RadioButton(
                            selected = task is Task.EquationSolver.Chord,
                            onClick = onClick,
                        )
                        Spacer(Modifier.size(16.dp))
                        Text("Chord method", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
                    }

                    Spacer(Modifier.size(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.EquationSolver.Newton() }
                        RadioButton(
                            selected = task is Task.EquationSolver.Newton,
                            onClick = onClick,
                        )
                        Spacer(Modifier.size(16.dp))
                        Text("Newton method", fontSize = 18.sp, modifier = Modifier.clickable(onClick = onClick))
                    }

                    Spacer(Modifier.size(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.EquationSolver.SimpleIteration() }
                        RadioButton(
                            selected = task is Task.EquationSolver.SimpleIteration,
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

                is Task.SystemSolver -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val onClick = { task = Task.SystemSolver.SimpleIteration() }
                        RadioButton(
                            selected = task is Task.SystemSolver.SimpleIteration,
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

                is Task.Undefined -> {
                    Box(Modifier.wrapContentSize()) {
                        Text("Select task before")
                    }
                }
            }
        }

        Spacer(Modifier.size(40.dp))

        //visualisation panel: graph + settings
        Row {
            if (settings == null) {//TODO: button to clear settings
                Box(Modifier.size(600.dp).weight(2f), contentAlignment = Alignment.Center) {
                    when (task) {
                        is Task.EquationSolver -> {
                            Column {
                                Text(
                                    modifier = Modifier.border(1.dp, Color.Black).padding(10.dp).clickable { },
                                    fontSize = 20.sp,
                                    text = "x² + 2x + 1 = 0"
                                )
                                Spacer(Modifier.size(10.dp))
                                Text(
                                    modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { },
                                    fontSize = 20.sp,
                                    text = "x² + 2x + 1 = 0"
                                )
                                Spacer(Modifier.size(10.dp))
                                Text(
                                    modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { },
                                    fontSize = 20.sp,
                                    text = "x² + 2x + 1 = 0"
                                )
                            }
                        }

                        is Task.SystemSolver -> {
                            Column {
                                Text(
                                    modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { },
                                    fontSize = 20.sp,
                                    text = "{x² + 2x + 1 = 0, x² + 2x + 1 = 0}"
                                )
                                Spacer(Modifier.size(10.dp))
                                Text(
                                    modifier = Modifier.border(1.dp, Color.Black).padding(3.dp).clickable { },
                                    fontSize = 20.sp,
                                    text = "{x² + 2x + 1 = 0, x² + 2x + 1 = 0}"
                                )
                            }
                        }

                        is Task.Undefined -> {
                            Text("Select task first")
                        }
                    }
                }
            } else {
                Box(Modifier.size(500.dp).background(Color.Magenta))
            }

            Spacer(Modifier.size(20.dp))

            Box(Modifier.weight(1f)) {
//                SettingsPanel(Settings()) {}//TODO
                when (task) {
                    is Task.EquationSolver -> {
                        Column {
                            Text("Initial value")
                        }
                        when (task) {
                            is Task.EquationSolver.Chord -> {
                                //range
                            }

                            is Task.EquationSolver.SimpleIteration -> {
                                //range
                            }

                            is Task.EquationSolver.Newton -> {
                                //double
                            }

                            is Task.EquationSolver.Undefined -> {}
                            else -> {
                                error("Type miss match")
                            }
                        }
                    }

                    is Task.SystemSolver -> {

                    }

                    is Task.Undefined -> {

                    }
                }
            }
        }

    }
}
