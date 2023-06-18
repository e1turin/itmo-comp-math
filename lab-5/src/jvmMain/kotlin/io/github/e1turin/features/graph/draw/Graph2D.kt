package io.github.e1turin.features.graph.draw

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
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.point.Point
import io.github.e1turin.entities.point.PointsStore
import io.github.e1turin.shared.lib.compose.Position
import io.github.e1turin.shared.lib.compose.Random
import io.github.e1turin.shared.lib.compose.drawText
import io.github.e1turin.shared.lib.std.pretty
import kotlin.math.max
import kotlin.math.min


@Composable
fun Graph2D(modifier: Modifier = Modifier, step: Dp = 1.dp) {
    val points by PointsStore.points

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

            val dx = (maxX - minX).let { if (it == 0.0) 1.0 else it }
            val dy = (maxY - minY).let { if (it == 0.0) 1.0 else it }

            val greatestDimension = if (dx > dy) size.width else size.height
            val greatestRangeLength = max(dx, dy)
            val scale = (greatestDimension - 2 * padding) / greatestRangeLength

            fun calculateX(x: Double): Float {
                return ((x - minX) * scale).toFloat() + padding
            }

            fun calculateY(y: Double): Float {
                return size.height - (((y - minY) * scale).toFloat() + padding)
            }

            fun axes(color: Color) {
                drawLine(
                    color = color,
                    start = Offset(
                        x = padding / 2,
                        y = size.height - padding / 2
                    ),
                    end = Offset(
                        x = padding / 2,
                        y = padding / 2
                    )
                )

                drawLine(
                    color = color,
                    start = Offset(
                        x = padding / 2,
                        y = size.height - padding / 2
                    ),
                    end = Offset(
                        x = size.width - padding / 2,
                        y = size.height - padding / 2
                    )
                )
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

                    //label on horizontal
                    rotate(
                        degrees = 45F,
                        pivot = Offset(
                            x = calculateX(p.x),
                            y = size.height - padding / 2
                        )
                    ) {
                        drawText(
                            text = p.x.pretty(),
                            position = Position(
                                x = calculateX(p.x),
                                y = size.height - padding / 2 + 20F
                            ),
                            color = Color.Black
                        )

                    }

                    //label on vertical
                    drawText(
                        text = p.y.pretty(),
                        position = Position(
                            x = 0F,
                            y = calculateY(p.y) + 10F
                        ),
                        color = Color.Black
                    )
                }
            }

            fun plot(function: (Double) -> Double, step: Float, color: Color = Color.Random) {
                val intervals = ((greatestDimension) / step).toInt() + 1

                var current = padding

                repeat(intervals) {
                    val next = current + step
                    val startY = calculateY(function((current - padding) / scale + minX))
                    val endY = calculateY(function((next - padding) / scale + minX))

                    if (
                        padding <= startY && startY < size.height - padding &&
                        padding < endY && endY < size.height - padding
                    ) {
                        drawLine(
                            color,
                            start = Offset(
                                x = current,
                                y = startY
                            ),
                            end = Offset(
                                x = next,
                                y = endY
                            ),
                            strokeWidth = 2F
                        )
                    }

                    current = next
                }
            }

            fun drawScene() {
                axes(Color.Black)
                scatter(points)
//                approximations.forEach {
//                    val approximation = it.first
//                    plot(approximation.function, step.toPx(), approximation.color)
//                }
            }

            drawScene()
        }
    }
}

