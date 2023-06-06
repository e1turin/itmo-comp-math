package io.github.e1turin.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import io.github.e1turin.feature.data.select.SelectFilePathButton
import io.github.e1turin.feature.graph.draw.Graph2D
import java.io.File

@Composable
fun MainPageUI(model: MainActivity) {
    Column(Modifier.fillMaxSize()) {
        Graph2D(Modifier.weight(1F))
        Row {
            SelectFilePathButton { filePath ->
                if (filePath != null) model.loadPointsFromFile(File(filePath))
            }
            Button(onClick = { model.calculateApproximations() }) { Text("Calc") }
            SelectFilePathButton { filePath ->
                if (filePath != null) model.savePointsToFile(File(filePath))
            }
        }
    }

}