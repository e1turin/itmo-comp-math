package io.github.e1turin.widgets.data.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointStore
import io.github.e1turin.feature.data.edit.EditPoints

@Composable
fun PointsPresenter(modifier: Modifier = Modifier) {
    val points by PointStore.points
    val changes = remember { linkedMapOf<Int, Point>() }

    Column {
        EditPoints(
            Modifier.weight(1F),
            points,
            onPointChange = { id, point -> changes[id] = point }
        )
        Button(onClick = {
            if (changes.isNotEmpty()) {
                PointStore.onAllPointsEdit(changes)
                changes.clear()
            }
        }) {
            Text("Submit")
        }
    }
}