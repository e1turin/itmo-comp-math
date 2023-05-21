package io.github.e1turin.output.view.entities.task.model

sealed interface TaskType {
    sealed interface Equation: TaskType {
        object Newton: Equation
        object Chord: Equation
        object SimpleIteration: Equation
        object Undefined: Equation
    }

    sealed interface System: TaskType {
        object SimpleIteration: System
        object Undefined: System
    }

    object Undefined: TaskType
}
