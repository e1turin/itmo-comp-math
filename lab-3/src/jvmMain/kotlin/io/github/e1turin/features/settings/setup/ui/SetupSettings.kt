package io.github.e1turin.features.settings.setup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.entities.settings.model.SettingsHolder
import io.github.e1turin.shared.lib.pretty
import io.github.e1turin.shared.ui.DoubleNumberInput
import io.github.e1turin.shared.ui.IntNumberInput


@Composable
fun SetupSettings(modifier: Modifier = Modifier) {
    val props by SettingsHolder.props

    val inputModifier = Modifier.fillMaxWidth()


    LazyColumn(
        userScrollEnabled = true,
        modifier = modifier
    ) {
        item {
            Property("divisions = ${props.divisions}", propertyModifier) {
                IntNumberInput(
                    value = props.divisions,
                    condition = { it > 1 },
                    onValueChange = SettingsHolder::onDivisionsChange,
                    modifier = inputModifier
                )
            }
        }

        item {
            Property("accuracy = ${props.accuracy.pretty()}", propertyModifier) {
                DoubleNumberInput(
                    value = props.accuracy,
                    condition = { it.isFinite() && it > 0 },
                    onValueChange = SettingsHolder::onAccuracyChange,
                    modifier = inputModifier
                )
            }
        }

        item {
            Property("range start = ${props.range.start.pretty()}", propertyModifier) {
                DoubleNumberInput(
                    value = props.range.start,
                    condition = { it.isFinite() && it < props.range.endInclusive },
                    onValueChange = SettingsHolder::onRangeStartChange,
                    modifier = inputModifier
                )
            }
        }

        item {
            Property("range end = ${props.range.start.pretty()}", propertyModifier) {
                DoubleNumberInput(
                    value = props.range.endInclusive,
                    condition = { it.isFinite() && it > props.range.start },
                    onValueChange = SettingsHolder::onRangeEndChange,
                    modifier = inputModifier
                )
            }
        }
    }

}

private val propertyModifier = Modifier.fillMaxWidth()

@Composable
private fun Property(title: String, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = Modifier.padding(5.dp)
        )
        content()
    }
}
