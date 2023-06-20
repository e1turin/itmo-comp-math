package io.github.e1turin.features.points.calculate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import io.github.e1turin.shared.config.samples.availableFunctions
import io.github.e1turin.shared.lib.compose.DoubleNumberInput
import io.github.e1turin.shared.lib.compose.Dropdown
import io.github.e1turin.shared.lib.compose.IntNumberInput
import kotlin.math.abs

@Composable
fun CalculatePoints(
    visible: Boolean,
    modifier: Modifier = Modifier,
    onComplete: (InputData?) -> Unit
) {
    var inputRange by remember { mutableStateOf(-3.0..3.0) }
    var inputDivisions by remember { mutableStateOf(4) }
    var selectedFunction by remember { mutableStateOf<String>(availableFunctions.keys.first().toString()) }


    Window(
        visible = visible,
        onCloseRequest = { onComplete(null) }
    ) {

        Column {
            LazyColumn {
                item {
                    Text("Range:")
                    DoubleNumberInput(
                        value = inputRange.start,
                        condition = { it.isFinite() && abs(it) < 10_000 && it < inputRange.endInclusive },
                        onValueChange = { inputRange = it..inputRange.endInclusive },
                        label = { Text("start") },
                        Modifier
                    )
                    DoubleNumberInput(
                        value = inputRange.start,
                        condition = { it.isFinite() && abs(it) < 10_000 && it > inputRange.start },
                        onValueChange = { inputRange = inputRange.start..it },
                        label = { Text("end") },
                        Modifier
                    )
                }

                item {
                    Text("Divisions:")
                    IntNumberInput(
                        value = inputDivisions,
                        condition = { it > 1 && it < 100_000 },
                        onValueChange = { inputDivisions = it },
                        label = { Text("divisions") },
                        Modifier
                    )
                }

                item {
                    Text("Function:")
                    Dropdown(
                        options = availableFunctions.keys.toList(),
                        selected = selectedFunction,
                        onSelect = { selectedFunction = it }
                    )
                }
            }

            Button(onClick = {
                onComplete(
                    InputData(
                        range = inputRange, divisions = inputDivisions, functionLabel = selectedFunction
                    )
                )
            }) {
                Text("Submit")
            }

        }
    }
}