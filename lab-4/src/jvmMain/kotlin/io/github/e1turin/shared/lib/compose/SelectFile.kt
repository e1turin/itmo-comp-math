package io.github.e1turin.shared.lib.compose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.darkrockstudios.libraries.mpfilepicker.FilePicker


/**
 * @param [onSelect] callback which is invoked with null if file was not selected.
 */
@Composable
fun SelectFilePathButton(
    modifier: Modifier = Modifier,
    onSelect: (String?) -> Unit,
    content: @Composable RowScope.()->Unit,
) {
    var show by remember { mutableStateOf(false) }

    Button(
        modifier = modifier,
        onClick = { show = true },
        content = content
    )

    FilePicker(
        show = show,
        onFileSelected = { file ->
            onSelect(file?.path)
            show = false
        }
    )
}