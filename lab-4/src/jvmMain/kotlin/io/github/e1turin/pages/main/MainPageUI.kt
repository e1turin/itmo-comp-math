package io.github.e1turin.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.feature.approximation.calculate.CalculateApproximationsButton
import io.github.e1turin.feature.data.load.LoadPointsButton
import io.github.e1turin.feature.data.offload.OffloadPointsButton
import io.github.e1turin.feature.graph.draw.Graph2D
import io.github.e1turin.shared.lib.cringe.DelicateCringeApi
import io.github.e1turin.widgets.data.presenter.PointsPresenter
import io.github.e1turin.widgets.graph.presenter.GraphPresenter
import org.jetbrains.skiko.hostArch

@OptIn(DelicateCringeApi::class)
@Composable
fun MainPageUI(model: MainActivity) {
    Row {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier) {
                LoadPointsButton(Modifier, model)
                OffloadPointsButton(Modifier, model)
            }
            PointsPresenter(Modifier)
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GraphPresenter(Modifier.weight(1F))
            CalculateApproximationsButton("Calculate", model, Modifier)
        }
    }

}