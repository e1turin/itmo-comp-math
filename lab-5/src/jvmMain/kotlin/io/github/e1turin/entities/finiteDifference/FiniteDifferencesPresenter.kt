package io.github.e1turin.entities.finiteDifference

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.interpolations.InterpolationsStore
import io.github.e1turin.entities.point.PointsStore
import io.github.e1turin.shared.lib.std.pretty

@Composable
fun FiniteDifferencesPresenter(
    modifier: Modifier = Modifier,
) {
    val fds by FiniteDifferencesStore.finiteDifferences
    val interpolations by InterpolationsStore.interpolations
    val inputParam by PointsStore.inspectingParam

    Column {
        SelectionContainer {
            LazyRow(modifier) {
                itemsIndexed(
                    items = fds,
                    key = { index: Int, item -> "$index$item" }
                ) { index, fdsi ->
                    Column(Modifier.width(IntrinsicSize.Max)) {
                        Text("Dy$index")

                        fdsi.forEach { fdi ->
                            Text(
                                text = fdi.pretty(),
                                modifier = Modifier.border(2.dp, Color.Black).padding(10.dp).fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }


    }
}