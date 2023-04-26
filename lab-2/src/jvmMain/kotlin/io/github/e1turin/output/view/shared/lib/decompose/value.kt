package io.github.e1turin.output.view.shared.lib.decompose

import com.arkivanov.decompose.value.MutableValue

inline fun <T : Any> MutableValue<T>.mutate(change: T.() -> T) {
    value = this.value.change()
}