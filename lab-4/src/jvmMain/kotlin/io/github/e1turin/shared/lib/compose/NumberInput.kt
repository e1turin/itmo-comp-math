package io.github.e1turin.shared.lib.compose


import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.e1turin.shared.lib.std.pretty

@Composable
fun DoubleNumberInput(
    value: Double,
    condition: (Double) -> Boolean = { true },
    onValueChange: (Double) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentValue by remember { mutableStateOf(value) }
    var inputText by remember { mutableStateOf(currentValue.pretty()) }

    TextField(
        value = inputText,
        onValueChange = { input ->
            inputText = input.trim()
            val inputDouble = inputText.replace(',', '.').toDoubleOrNull()

            if (inputDouble != null && currentValue != inputDouble && condition(inputDouble)) {
                currentValue = inputDouble
                onValueChange(inputDouble)
            }
        },
        modifier = modifier,
        label = label
    )
}

@Composable
fun IntNumberInput(
    value: Int,
    condition: (Int) -> Boolean = { true },
    onValueChange: (Int) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentValue by remember { mutableStateOf(value) }
    var inputText by remember { mutableStateOf(currentValue.toString()) }

    TextField(
        value = inputText,
        onValueChange = { input ->
            inputText = input.trim()
            val inputInt = inputText.toIntOrNull()

            if (inputInt != null && currentValue != inputInt && condition(inputInt)) {
                currentValue = inputInt
                onValueChange(inputInt)
            }
        },
        modifier = modifier,
        label = label
    )
}