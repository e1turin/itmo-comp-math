package io.github.e1turin.feature.data.select

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.darkrockstudios.libraries.mpfilepicker.FilePicker


/**
 * @param [onSelect] callback which is invoked with null if file was not selected.
 */
@Composable
fun SelectFilePathButton(onSelect: (String?) -> Unit) {
    var show by remember { mutableStateOf(false) }

    Button(
        onClick = { show = true }
    ) {
        Text("Select file")
    }

    FilePicker(
        show = show,
        onFileSelected = { file ->
            onSelect(file?.path)
            show = false
        }
    )
}