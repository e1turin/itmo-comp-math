package io.github.e1turin.shared.lib

import androidx.compose.runtime.MutableState

fun <T> MutableState<T>.mutate(mutator: T.() -> T) {
    this.value = this.value.mutator()
}
