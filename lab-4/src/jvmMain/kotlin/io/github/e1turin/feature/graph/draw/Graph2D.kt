package io.github.e1turin.feature.graph.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.onGloballyPositioned
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointStore
import kotlin.math.max


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Graph2D(modifier: Modifier = Modifier, step: Double = 0.01) {
    val approximations by ApproximationsStore.approximations
    val points by PointStore.points
    val sortedPoints = points.sortedBy { it.x }

    var maxX: Double = 0.0
    var minX: Double = 0.0
    var maxY: Double = 0.0
    var minY: Double = 0.0

    if (sortedPoints.isNotEmpty()) {
        minX = sortedPoints.first().x
        maxX = sortedPoints.last().x
        minY = sortedPoints.first().y
        maxY = sortedPoints.last().y
    } else {
        println("points empty")
    }

    val padding = 40F

    val dx = maxX - minX
    val dy = maxY - minY

    Box(modifier) {
        Canvas(
            Modifier
                .fillMaxSize()
                .clipToBounds()

        ) {
            val greatestDimension = if (dx > dy) size.width else size.height
            val greatestRangeLength = max(dx, dy)
            val scale = (greatestDimension - 2 * padding) / greatestRangeLength

            fun scatter(points: List<Point>) {
                for (p in points) {
                    drawCircle(
                        color = Color.Cyan,
                        radius = 5F,
                        center = Offset(
                            x = ((p.x - minX) * scale).toFloat() + padding,
                            y = ((p.y - minY) * scale).toFloat() + padding
                        )
                    )
                }
            }

            fun plot(function: (Double) -> Double, step: Double) {
                val intervals = ((greatestRangeLength) / step).toInt() + 1

                var current = minX

                repeat(intervals) {
                    val next = current + step

                    drawLine(
                        Color.Blue,
                        start = Offset(
                            x = ((current - minX) * scale).toFloat() + padding,
                            y = ((function(current) - minY) * scale).toFloat() + padding
                        ),
                        end = Offset(
                            x = ((next - minX) * scale).toFloat() + padding,
                            y = ((function(next) - minY) * scale).toFloat() + padding
                        ),
                        strokeWidth = 2F
                    )

                    current = next
                }
            }

            scatter(sortedPoints)

            approximations.forEach {
                val function = it.function
                println(it.textView())
                plot(function, step)
            }


        }
    }
}

