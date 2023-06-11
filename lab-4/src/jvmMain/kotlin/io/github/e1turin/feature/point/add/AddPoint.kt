package io.github.e1turin.feature.point.add

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointStore
import io.github.e1turin.entities.point.ui.EditPointItem

@Composable
fun AddPoint(
    modifier: Modifier = Modifier,
) {
    var point by remember { mutableStateOf(Point(0.0, 0.0)) }

    Row(modifier) {
        EditPointItem(
            point = point,
            onEdit = { p -> point = p },
            Modifier.weight(1F)
        )
        Button(onClick = { PointStore.onPointAppend(point) }) {
            Text("Add")
        }
    }
}