package io.github.e1turin.shared.config.samples

import io.github.e1turin.entities.interpolations.Interpolation
import io.github.e1turin.entities.interpolations.LagrangeInterpolation
import io.github.e1turin.entities.interpolations.NewtonInterpolation

val availableInterpolations = listOf<Interpolation>(
    LagrangeInterpolation(),
    NewtonInterpolation(),
//    GaussInterpolation()
)