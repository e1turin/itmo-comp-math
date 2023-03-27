package io.github.e1turin.output.view.entities.form.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.output.view.entities.form.model.Settings

@Composable
fun StepSlider(
    initialValue: Int,
    minValue: Int,
    maxValue: Int,
    label: String,
    onChange: (Int) -> Unit,
) {
    var value by remember { mutableStateOf(initialValue.toFloat()) }
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Row {
            Text(text = "$label: ", fontSize = 14.sp)
            Text(text = "%d".format(value.toInt()), fontSize = 14.sp)
        }
        Slider(
            value = value,
            valueRange = minValue.toFloat()..maxValue.toFloat(),
            onValueChange = { value = it; onChange(value.toInt()) },
            onValueChangeFinished = { onChange(value.toInt()) }
        )
    }
}

val padding = Modifier.padding(10.dp)

@Composable
fun PropertySlider(
    initialValue: Double,
    minValue: Double,
    maxValue: Double,
    label: String,
    onChange: (Double) -> Unit,
) {
    var value by remember { mutableStateOf(initialValue.toFloat()) }
    Column(
        modifier = padding
    ) {
        Row {
            Text(text = "$label: ", fontSize = 14.sp)
            Text(text = "%.2f".format(value), fontSize = 14.sp)
        }
        Slider(
            value = value,
            valueRange = minValue.toFloat()..maxValue.toFloat(),
            onValueChange = { value = it; onChange(value.toDouble()) },
            onValueChangeFinished = { onChange(value.toDouble()) }
        )
    }
}

const val MIN_FUNCTION_RANGE_START = -10.0
const val MAX_FUNCTION_RANGE_START = 9.0
const val MIN_FUNCTION_RANGE_END = -9.0
const val MAX_FUNCTION_RANGE_END = 10.0
const val MIN_GRID_STEP = 0.001
const val MAX_GRID_STEP = 2.0
const val MIN_DENSITY = 0.001
const val MAX_DENSITY = 0.1


@Composable
fun SettingsPanel(settings: Settings, onValueChange: (Settings) -> Unit) {
    LazyColumn(
        modifier = Modifier.width(400.dp)
    ) {
        item {
            Text(
                "Settings",
                color = MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
            )
        }
        item {
            PropertySlider(
                settings.functionRangeStart,
                MIN_FUNCTION_RANGE_START,
                MAX_FUNCTION_RANGE_START,
                "Function range start"
            ) { newValue -> onValueChange(settings.copy(functionRangeStart = newValue)) }
        }
        item {
            PropertySlider(
                settings.functionRangeEnd,
                MIN_FUNCTION_RANGE_END,
                MAX_FUNCTION_RANGE_END,
                "Function range end"
            ) { newValue -> onValueChange(settings.copy(functionRangeEnd = newValue)) }
        }
        item {
            PropertySlider(
                settings.graphGridStep,
                MIN_GRID_STEP,
                MAX_GRID_STEP,
                "Grid step"
            ) { newValue -> onValueChange(settings.copy(graphGridStep = newValue)) }
        }
        item {
            PropertySlider(
                settings.graphDensity,
                MIN_DENSITY,
                MAX_DENSITY,
                "Graph density"
            ) { newValue -> onValueChange(settings.copy(graphDensity = newValue)) }
        }
    }
}

