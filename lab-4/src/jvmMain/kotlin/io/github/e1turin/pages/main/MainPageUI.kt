package io.github.e1turin.pages.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray, RoundedCornerShape(5.dp))
                .padding(5.dp),
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LoadPointsButton(Modifier, model)
                OffloadPointsButton(Modifier, model)
            }
            PointsPresenter(Modifier)
        }

        Spacer(Modifier.width(20.dp))

        Column(
            modifier = Modifier
                .weight(1F)
                .border(2.dp, Color.LightGray, RoundedCornerShape(5.dp))
                .padding(10.dp),
        ) {
            GraphPresenter(Modifier.weight(1F))
            CalculateApproximationsButton("Compute", model, Modifier)
        }

        Spacer(Modifier.width(20.dp))

        Column(
            modifier = Modifier
                .weight(1F)
                .background(Color.LightGray, RoundedCornerShape(5.dp))
                .padding(5.dp)
                .fillMaxHeight(),
        ) {
            ApproximationsPresenter(Modifier)

            SelectFilePathButton(
                Modifier,
                onSelect = { filepath ->
                    if (filepath != null) model.saveApproximationsPrintToFile(File(filepath))
                }
            ) {
                Text("Save result")
            }
        }
    }
}