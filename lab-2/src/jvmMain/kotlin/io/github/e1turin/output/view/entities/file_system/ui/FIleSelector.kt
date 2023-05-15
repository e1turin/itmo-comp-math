package io.github.e1turin.output.view.entities.file_system.ui

import androidx.compose.runtime.Composable
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.darkrockstudios.libraries.mpfilepicker.FileSelected


@Composable
fun FileSelector(
    show: Boolean = false,
    onFileSelected: FileSelected
) {
    val fileExtensions = listOf("json")

    FilePicker(
        show = show,
//        fileExtensions = fileExtensions,
        onFileSelected = onFileSelected
    )
}

