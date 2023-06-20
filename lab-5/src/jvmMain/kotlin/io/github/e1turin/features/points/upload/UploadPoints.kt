package io.github.e1turin.features.points.upload

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.pages.MainActivity
import io.github.e1turin.shared.lib.compose.SelectFilePathButton
import java.io.File

@Composable
fun UploadPointsButton(
    modifier: Modifier = Modifier,
    model: MainActivity,
) {
    SelectFilePathButton(modifier, onSelect = { filePath ->
        if (filePath != null) model.loadPointsFromFile(File(filePath))
    }) {
        Text("Load from file")
    }
}
