package io.github.e1turin.widgets.finiteDifferences.output

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.e1turin.entities.finiteDifference.FiniteDifferencesPresenter
import io.github.e1turin.pages.MainActivity
import io.github.e1turin.shared.lib.compose.SelectFilePathButton
import java.io.File

@Composable
fun FiniteDifferencesOutput(
    model: MainActivity,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Button(onClick = { model.computeFD() }) {
            Text("Compute")
        }

        FiniteDifferencesPresenter(Modifier)

        SelectFilePathButton(
            Modifier,
            onSelect = { filepath ->
                if (filepath != null) model.saveFDPrintToFile(File(filepath))
            }
        ) {
            Text("Save result")
        }
    }
}