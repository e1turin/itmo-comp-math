package io.github.e1turin.feature.approximation.calculate

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.pages.main.MainActivity

@Composable
fun CalculateApproximationsButton(
    label: String,
    model: MainActivity,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        onClick = { model.calculateNewApproximations() }
    ) {
        Text(label)
    }
}