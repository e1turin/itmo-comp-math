package io.github.e1turin.pages.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.feature.approximation.calculate.CalculateApproximationsButton
import io.github.e1turin.feature.data.load.LoadPointsButton
import io.github.e1turin.feature.data.offload.OffloadPointsButton
import io.github.e1turin.shared.lib.compose.SelectFilePathButton
import io.github.e1turin.widgets.data.presenter.ApproximationsPresenter
import io.github.e1turin.widgets.data.presenter.PointsPresenter
import io.github.e1turin.widgets.graph.presenter.GraphPresenter
import java.io.File

@Composable
fun MainPageUI(model: MainActivity) {
    Row(Modifier) {
        Column(
            modifier = Modifier,
        ) {
            Row(Modifier) {
                LoadPointsButton(Modifier, model)
                OffloadPointsButton(Modifier, model)
            }
            PointsPresenter(Modifier)
        }

        Spacer(Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GraphPresenter(Modifier.weight(1F))
            CalculateApproximationsButton("Calculate", model, Modifier)
        }

        Spacer(Modifier.width(20.dp))

        Column {
            ApproximationsPresenter(Modifier)

            SelectFilePathButton(
                Modifier,
                onSelect = { filepath ->
                    model.saveApproximationsPrintToFile(File(filepath))
                }
            ) {
                Text("Save")
            }
        }
    }
}