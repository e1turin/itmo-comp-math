package io.github.e1turin.feature.graph.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import kotlin.math.min


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Graph2D(modifier: Modifier = Modifier, step: Double = 0.01) {
    val approximations by ApproximationsStore.approximations
    val points by PointStore.points

    var maxX = 0.0
    var minX = 0.0

    var maxY = 0.0
    var minY = 0.0

    points.forEach {
        minX = min(minX, it.x)
        maxX = max(maxX, it.x)

        minY = min(minY, it.y)
        maxY = max(maxY, it.y)
    }

    Box(modifier) {
        Canvas(
            Modifier
                .fillMaxSize()
                .clipToBounds()
        ) {
            //TODO: Draw Grid

            if (points.isEmpty()) {
                println("[Graph2D]points is empty")
                return@Canvas
            }

            val padding = 80F

            val dx = maxX - minX
            val dy = maxY - minY

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
                    val startY = calculateY(function(current))
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

            scatter(points)

            approximations.forEach {
                val approximation = it.first
                println(approximation.textView())
                plot(approximation.function, step, approximation.color)
            }
        }
    }
}

