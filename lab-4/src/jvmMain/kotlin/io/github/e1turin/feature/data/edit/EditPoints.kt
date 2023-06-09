package io.github.e1turin.feature.data.edit

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.point.Point
import io.github.e1turin.feature.point.edit.EditPointItem

@Composable
fun EditPoints(
    modifier: Modifier = Modifier,
    points: List<Point>,
    onPointChange: (Int, Point) -> Unit
) {
    LazyColumn(modifier) {
        itemsIndexed(points) { id, point ->
            EditPointItem(
                point,
                onEdit = { p -> onPointChange(id, p) }
            )
        }
    }

}