package io.github.e1turin.entities.integrator.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.integrator.model.IntegrationResultHolder
import io.github.e1turin.shared.lib.pretty
import io.github.e1turin.shared.ui.Property

@Composable
fun SolutionHolderUI(modifier: Modifier = Modifier) {
    val data by IntegrationResultHolder.data

    LazyColumn(
        userScrollEnabled = true,
        modifier = modifier,
    ) {
        if (data == null) {
            item {
                Text("Initiate solving process with button")
            }
        } else {
            item {
                Property("area = ${data?.area?.pretty()}") { }
            }

            item {
                Property("precision = ${data?.precision?.pretty()}") { }
            }

            item {
                Property("divisions = ${data?.divisions}") { }
            }

            item {
                Property("convergence : ${data?.convergence}") { }
            }
        }
    }
}