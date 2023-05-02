package io.github.e1turin.output.view.shared.ui.range

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.e1turin.output.view.shared.lib.std.*


@Preview
@Composable
fun RangePickerPreview() {
    val range by remember { mutableStateOf(-1F..3F) }

    Box(Modifier.fillMaxSize()) {
        RangePicker(
            modifier = Modifier.fillMaxSize(),
            buttonModifier = Modifier,
            range = range,
            onMoveLeft = { range.slideToLowestBy(1F) },
            onMoveRight = { range.slideToHighestBy(1F) },
            onShrink = { range.shrinkBy(1F) },
            onStretch = { range.stretchBy(1F) },
        )
    }
}

@Composable
fun RangePicker(
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    range: ClosedFloatingPointRange<Float>,
    onMoveLeft: () -> Unit = {},
    onMoveRight: () -> Unit = {},
    onStretch: () -> Unit = {},
    onShrink: () -> Unit = {},
) {
    val buttons = listOf(
        "⯇" to onMoveLeft,
        "⯈⯇" to onShrink,
        "⯇⯈" to onStretch,
        "⯈" to onMoveRight
    )

    Column(
        modifier = modifier.wrapContentSize().padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            for ((title, onClick) in buttons) {
                Button(
                    onClick = onClick,
                    modifier = buttonModifier.weight(1F),
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(
                            start = 3.dp,
                            top = 2.dp,
                            end = 3.dp,
                            bottom = 6.dp
                        ),
                        fontSize = 14.sp
                    )
                }.also { Spacer(Modifier.size(3.dp)) }
            }
        }.also { Spacer(Modifier.size(5.dp)) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RoundedCornerShape(5.dp))
                .padding(10.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "[${range.start.pretty()} .. ${range.endInclusive.pretty()}]", fontSize = 18.sp,
            )
        }
    }
}