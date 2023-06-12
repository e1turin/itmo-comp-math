package io.github.e1turin.feature.data.edit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.ui.EditPointItem

@Composable
fun EditPoints(
    modifier: Modifier = Modifier,
    points: List<Point>,
    onPointChange: (Int, Point) -> Unit,
    onPointDelete: (Int) -> Unit
) {
    LazyColumn(modifier) {
        itemsIndexed(
            items = points,
            key = { id, item -> "$id$item" }
        ) { id, point ->
            Row {
                Text(
                    text = (id + 1).toString(),
                    modifier = Modifier.padding(5.dp).width(30.dp)
                )
                EditPointItem(
                    point,
                    onEdit = { p -> onPointChange(id, p) }
                )
                Button(
                    onClick = { onPointDelete(id) }
                ) {
                    Text("Delete")
                }
            }
        }
    }
}