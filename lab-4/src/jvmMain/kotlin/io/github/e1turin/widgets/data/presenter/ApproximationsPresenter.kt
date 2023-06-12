package io.github.e1turin.widgets.data.presenter

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.e1turin.entities.approximation.ApproximationsStore

@Composable
fun ApproximationsPresenter(
    modifier: Modifier = Modifier,
) {
    val approximations by ApproximationsStore.approximations

    SelectionContainer {
        LazyColumn(modifier) {
            items(approximations) {
                val approximation = it.first
                Text(
                    text = approximation.print(),
                    modifier = Modifier,
                )
                Spacer(Modifier.size(10.dp))
            }
        }
    }
}