package io.github.e1turin.pages.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointStore
import io.github.e1turin.entities.reposotory.jsonFormat
import io.github.e1turin.feature.data.select.SelectFilePathButton
import io.github.e1turin.feature.graph.draw.Graph2D
import io.github.e1turin.shared.lib.cringe.DelicateCringeApi
import io.github.e1turin.shared.lib.cringe.fromCringeFormat
import io.github.e1turin.shared.lib.cringe.toCringeFormat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File

@OptIn(DelicateCringeApi::class)
@Composable
fun MainPageUI(model: MainActivity) {
    val approximations by ApproximationsStore.approximations
    var con: String by remember { mutableStateOf("") }
    Row {
        Column(
            Modifier
                .fillMaxHeight()
                .verticalScroll(state = rememberScrollState())
        ) {
            con = jsonFormat.encodeToString(PointStore.points.value).toCringeFormat()
            TextField(
                value = con,
                onValueChange = {
                    con = it
                    try {
                        val points = jsonFormat.decodeFromString<List<Point>>(it.fromCringeFormat())
                        PointStore.onPointsClean()
                        PointStore.onPointsAppend(points)
                    } catch (e: Exception) {
                        println("[MainPage]error while deserialization")
                    }
                }
            )
        }

        Column(Modifier.fillMaxSize()) {
            Box(Modifier.weight(1F).align(Alignment.End)) {
                Graph2D(Modifier)
                if (approximations.isNotEmpty()) {
                    Column {
                        approximations.forEach {
                            Text("y(x) = ${it.textView()}", color = it.color)
                        }
                    }
                }
            }
            Row {
                SelectFilePathButton { filePath ->
                    if (filePath != null) model.loadPointsFromFile(File(filePath))
                }
                Button(onClick = { model.calculateApproximations() }) { Text("Calc") }
                SelectFilePathButton { filePath ->
                    if (filePath != null) model.savePointsToFile(File(filePath))
                }
            }
        }
    }

}