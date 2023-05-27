package io.github.e1turin.features.settings.setup.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.settings.model.SettingsHolder
import io.github.e1turin.shared.config.functionStore
import io.github.e1turin.shared.lib.pretty
import io.github.e1turin.shared.ui.DoubleNumberInput
import io.github.e1turin.shared.ui.IntNumberInput
import io.github.e1turin.shared.ui.Property


@Composable
fun SetupSettings(modifier: Modifier = Modifier) {
    val props by SettingsHolder.params

    val inputModifier = Modifier.fillMaxWidth()
    val propertyModifier = Modifier.fillMaxWidth()

    LazyColumn(
        userScrollEnabled = true,
        modifier = modifier
    ) {
        item {
            Property("function id = ${props.functionId}", propertyModifier) {
                IntNumberInput(
                    value = props.functionId,
                    condition = { it >= 0 && it < functionStore.size },
                    onValueChange = SettingsHolder::onFunctionSelect,
                    modifier = inputModifier
                )
            }
        }

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
            Property("accuracy = ${props.precision.pretty()}", propertyModifier) {
                DoubleNumberInput(
                    value = props.precision,
                    condition = { it.isFinite() && it > 0 },
                    onValueChange = SettingsHolder::onPrecisionChange,
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

