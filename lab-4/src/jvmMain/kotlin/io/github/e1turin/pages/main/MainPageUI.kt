package io.github.e1turin.pages.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import io.github.e1turin.entities.approximation.ApproximationsStore
import io.github.e1turin.entities.point.PointStore

@Composable
fun MainPageUI(model: MainActivity) {
    val points by PointStore.points
    val approximations by ApproximationsStore.approximations

}