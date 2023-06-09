package io.github.e1turin.feature.point.edit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.lib.compose.DoubleNumberInput

@Composable
fun EditPointItem(
    point: Point,
    onEdit: (Point) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row {
        DoubleNumberInput(
            modifier = Modifier.width(100.dp),
            value = point.x,
            condition = { it.isFinite() },
            onValueChange = { onEdit(Point(it, point.y)) },
            label = { Text("x") }
        )
        DoubleNumberInput(
            modifier = Modifier.width(100.dp),
            value = point.y,
            condition = { it.isFinite() },
            onValueChange = { onEdit(Point(point.x, it)) },
            label = { Text("y") }
        )
    }
}