package io.github.e1turin.widgets.data.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointStore
import io.github.e1turin.feature.data.edit.EditPoints
import io.github.e1turin.feature.point.add.AddPoint

@Composable
fun PointsPresenter(modifier: Modifier = Modifier) {
    val points by PointStore.points
    val changes = remember { linkedMapOf<Int, Point>() }

    Column(modifier) {
        EditPoints(
            Modifier.weight(1F),
            points,
            onPointChange = { id, point -> changes[id] = point },
            onPointDelete = { id -> PointStore.onPointDelete(id) }
        )
        Spacer(Modifier.size(10.dp))
        AddPoint(Modifier.padding(10.dp))

        Button(
            modifier = Modifier,
            onClick = {
                if (changes.isNotEmpty()) {
                    PointStore.onAllPointsEdit(changes)
                    changes.clear()
                }
            }) {
            Text("Submit")
        }
    }
}