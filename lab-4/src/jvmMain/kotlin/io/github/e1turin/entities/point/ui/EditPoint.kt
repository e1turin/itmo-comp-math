package io.github.e1turin.entities.point.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.lib.compose.DoubleNumberInput

@Composable
fun EditPointItem(
    point: Point,
    onEdit: (Point) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        DoubleNumberInput(
            modifier = Modifier.width(100.dp).background(Color.White),
            value = point.x,
            condition = { it.isFinite() },
            onValueChange = { onEdit(Point(it, point.y)) },
            label = { Text("x") }
        )
        DoubleNumberInput(
            modifier = Modifier.width(100.dp).background(Color.White),
            value = point.y,
            condition = { it.isFinite() },
            onValueChange = { onEdit(Point(point.x, it)) },
            label = { Text("y") }
        )
    }
}