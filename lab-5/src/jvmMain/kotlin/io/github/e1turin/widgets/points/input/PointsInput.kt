package io.github.e1turin.widgets.points.input

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointsStore
import io.github.e1turin.features.points.add.AddPoint
import io.github.e1turin.features.points.calculate.CalculatePoints
import io.github.e1turin.features.points.edit.EditPoint
import io.github.e1turin.features.points.offload.OffloadPointsButton
import io.github.e1turin.features.points.upload.UploadPointsButton
import io.github.e1turin.pages.MainActivity
import io.github.e1turin.shared.lib.compose.DoubleNumberInput
import kotlin.math.abs

@Composable
fun PointsInput(
    model: MainActivity,
    modifier: Modifier = Modifier,
) {
    val points by PointsStore.points
    val changes = remember { mutableMapOf<Int, Point>() }
    var generateOpen by remember { mutableStateOf(false) }
    val inspectingParam by PointsStore.inspectingParam

    Column(modifier) {
        Row(Modifier) {
            UploadPointsButton(Modifier, model)
            Spacer(Modifier.size(5.dp))
            OffloadPointsButton(Modifier, model)
            Spacer(Modifier.size(5.dp))
            Button(onClick = { generateOpen = true }) {
                Text("Generate")

            }
            CalculatePoints(
                visible = generateOpen
            ) {
                if (it != null) model.generatePoints(it)
                generateOpen = false
            }
        }

        Column(Modifier) {
            LazyColumn(Modifier.weight(1F)) {
                itemsIndexed(items = points, key = { id, item -> "$id$item" }) { id, point ->
                    Row {
                        Text(
                            text = (id + 1).toString(),
                            modifier = Modifier.padding(5.dp).width(30.dp)
                        )

                        EditPoint(point, onEdit = { changes[id] = it })

                        Button(onClick = { PointsStore.onPointDelete(id) }) {
                            Text("Delete")
                        }
                    }
                }
                item(key = "addPoint") {
                    Row(Modifier.padding(5.dp)) {
                        Text(
                            text = " + ",
                            modifier = Modifier.padding(5.dp).width(30.dp)
                        )
                        AddPoint(Modifier)
                    }
                }
            }

            DoubleNumberInput(
                value = inspectingParam,
                condition = { it.isFinite() && abs(it) < 10_000 },
                onValueChange = PointsStore::onInspectingParamChange,
                label = { Text("Inspecting parameter") }
            )

            Button(onClick = {
                if (changes.isNotEmpty()) {
                    PointsStore.onAllPointsEdit(changes)
                    changes.clear()
                }
            }) {
                Text("Submit")
            }
        }

        // TODO: Point argument input
    }
}