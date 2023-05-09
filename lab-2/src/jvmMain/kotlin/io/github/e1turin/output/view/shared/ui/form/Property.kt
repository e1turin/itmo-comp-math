package io.github.e1turin.output.view.shared.ui.form

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun Property(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(start = 10.dp)) {
        Text(title)
            .also { Spacer(Modifier.size(10.dp)) }

        content()
    }
}
