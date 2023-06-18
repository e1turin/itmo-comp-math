package io.github.e1turin.widgets.graph.output

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.features.graph.draw.Graph2D
import io.github.e1turin.pages.MainActivity

@Composable
fun GraphOutput(
    model: MainActivity,
    modifier: Modifier = Modifier,
) {
    Graph2D(modifier)
}