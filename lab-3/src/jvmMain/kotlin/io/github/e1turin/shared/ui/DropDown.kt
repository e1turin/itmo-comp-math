package io.github.e1turin.shared.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun <T> Dropdown(
    options: List<T>,
    selected: T = options[0],
    modifier: Modifier = Modifier.size(250.dp, 32.dp)
        .clip(RoundedCornerShape(4.dp))
        .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(4.dp)),
    onSelect: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText: String by remember { mutableStateOf(selected.toString()) }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .clickable { expanded = !expanded },
    ) {
        Text(
            text = selectedOptionText,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp)
        )
        Icon(
            Icons.Filled.ArrowDropDown, "expand list",
            Modifier.align(Alignment.CenterEnd)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption.toString()
                        onSelect(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(selectionOption.toString())
                }
            }
        }
    }
}
