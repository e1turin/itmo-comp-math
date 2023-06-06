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
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointStore
import io.github.e1turin.shared.lib.compose.Random
import kotlin.math.max


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Graph2D(modifier: Modifier = Modifier, step: Double = 0.01) {
    val approximations by ApproximationsStore.approximations
    val points by PointStore.points

    val sortedPointsByX = points.sortedBy { it.x }
    var maxX: Double = 0.0
    var minX: Double = 0.0
    if (sortedPointsByX.isNotEmpty()) {
        minX = sortedPointsByX.first().x
        maxX = sortedPointsByX.last().x
    } else {
        println("points empty")
    }

    val sortedPointsByY = points.sortedBy { it.y }
    var maxY: Double = 0.0
    var minY: Double = 0.0
    if (sortedPointsByY.isNotEmpty()) {
        minY = sortedPointsByY.first().y
        maxY = sortedPointsByY.last().y
    } else {
        println("points empty")
    }

    val padding = 80F

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

            fun calculateX(x: Double): Float {
                return ((x - minX) * scale).toFloat() + padding
            }

            fun calculateY(y: Double): Float {
                return size.height - (((y - minY) * scale).toFloat() + padding)
            }

            fun scatter(points: List<Point>, color: Color = Color.Random) {
                for (p in points) {
                    drawCircle(
                        color = color,
                        radius = 5F,
                        center = Offset(
                            x = calculateX(p.x),
                            y = calculateY(p.y)
                        )
                    )
                }
            }

            fun plot(function: (Double) -> Double, step: Double, color: Color = Color.Random) {
                val intervals = ((greatestRangeLength) / step).toInt() + 1

                var current = minX

                repeat(intervals) {
                    val next = current + step
                    val startY =calculateY(function(current))
                    val endY = calculateY(function(next))

                    if (
                        padding <= startY && startY < size.height - padding &&
                        padding < endY && endY < size.height - padding
                    ) {
                        drawLine(
                            color,
                            start = Offset(
                                x = calculateX(current),
                                y = startY
                            ),
                            end = Offset(
                                x = calculateX(next),
                                y = endY
                            ),
                            strokeWidth = 2F
                        )
                    }

                    current = next
                }
            }

            scatter(sortedPointsByX)

            approximations.forEach {
                println(it.textView())
                plot(it.function, step, it.color)
            }
        }
    }
}

