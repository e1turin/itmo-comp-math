package io.github.e1turin.features.solution.initiate.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.integrator.model.IntegralFinder

@Composable
fun IntegrateButton(modifier: Modifier = Modifier) {
    Button(
        modifier = modifier,
        onClick = { IntegralFinder.integrate() },
    ) {
        Text("integrate")
    }
}