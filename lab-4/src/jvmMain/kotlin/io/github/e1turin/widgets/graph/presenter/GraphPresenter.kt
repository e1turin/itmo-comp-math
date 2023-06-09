package io.github.e1turin.widgets.graph.presenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.feature.graph.draw.Graph2D

@Composable
fun GraphPresenter(
    modifier: Modifier = Modifier,
) {
    val approximations by ApproximationsStore.approximations
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopEnd
    ) {
        Graph2D(Modifier)
        if (approximations.isNotEmpty()) {
            Column {
                approximations.forEachIndexed {id, it ->
                    Text("${id + 1}) y(x) = ${it.textView()}", color = it.color)
                }
            }
        }
    }
}