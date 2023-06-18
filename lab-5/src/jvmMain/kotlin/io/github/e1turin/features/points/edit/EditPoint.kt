package io.github.e1turin.features.points.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.point.Point
import io.github.e1turin.shared.lib.compose.DoubleNumberInput

@Composable
fun EditPoint(
    point: Point,
    onEdit: (Point) -> Unit,
    modifier: Modifier = Modifier,
) {
    var tmpPoint by remember { mutableStateOf(point) }

    Row(modifier) {
        DoubleNumberInput(
            modifier = Modifier.width(100.dp).background(Color.White),
            value = tmpPoint.x,
            condition = { it.isFinite() },
            onValueChange = { tmpPoint = tmpPoint.copy(x = it).also(onEdit) },
            label = { Text("x") }
        )

        DoubleNumberInput(
            modifier = Modifier.width(100.dp).background(Color.White),
            value = tmpPoint.y,
            condition = { it.isFinite() },
            onValueChange = { tmpPoint = tmpPoint.copy(y = it).also(onEdit) },
            label = { Text("y") }
        )
    }
}