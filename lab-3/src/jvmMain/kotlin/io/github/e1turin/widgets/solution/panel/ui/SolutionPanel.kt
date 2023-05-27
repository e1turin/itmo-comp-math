package io.github.e1turin.widgets.solution.panel.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.features.solution.initiate.ui.IntegrateButton
import io.github.e1turin.features.solution.present.ui.PresentSolution

@Composable
fun SolutionPanel(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Solution", fontSize = 26.sp,
                modifier = Modifier.padding(5.dp)
            )
            IntegrateButton(Modifier.padding(5.dp))
        }
        Divider(Modifier.fillMaxWidth(0.75F), thickness = 2.dp, color = Color.DarkGray)
        PresentSolution(Modifier.fillMaxWidth())
    }
}