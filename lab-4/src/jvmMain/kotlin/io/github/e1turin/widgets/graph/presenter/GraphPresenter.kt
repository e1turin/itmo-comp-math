package io.github.e1turin.widgets.graph.presenter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.feature.graph.draw.Graph2D
import io.github.e1turin.shared.lib.std.pretty

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
            SelectionContainer {
                Column(Modifier.width(IntrinsicSize.Max)) {
                    approximations.asSequence()
                        .sortedBy { it.second }
                        .forEachIndexed { id, it ->
                            val approximation = it.first
                            val deviance = it.second
                            Row(
                                Modifier.fillMaxWidth(),
                                Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${id + 1}) y(x) = ${approximation.textView()} , ",
                                    color = approximation.color
                                )
                                Text(
                                    text = "std = ${deviance.pretty()}",
                                    color = approximation.color
                                )
                            }
                        }
                }
            }
        }
    }
}