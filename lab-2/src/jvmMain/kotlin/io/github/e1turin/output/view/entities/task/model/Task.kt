package io.github.e1turin.output.view.entities.task.model

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.github.e1turin.output.view.entities.settings.model.*
import io.github.e1turin.output.view.entities.settings.model.DefaultEquationSettings

open class Task(
    taskType: TaskType,
    settings: Settings,
) {
    private val _taskType = MutableValue(taskType)
    val taskType: Value<TaskType> = _taskType

    private val _settings = MutableValue(settings)
    val settings: Value<Settings> = _settings

    fun onSelectOther(task: TaskType) {
        println("[Task.kt]$task")
        _taskType.value = task
        _settings.value = when (task) {
            is TaskType.Equation -> mapEquationSettings(task)
            is TaskType.System -> mapSystemSettings(task)
            TaskType.Undefined -> UndefinedDefaultSettings
        }
    }
}

private inline fun mapEquationSettings(taskType: TaskType.Equation) = when (taskType) {
    TaskType.Equation.Newton -> NewtonEquationSettings()
    TaskType.Equation.Chord -> TODO()
    TaskType.Equation.SimpleIteration -> TODO()
    TaskType.Equation.Undefined -> DefaultEquationSettings
}

private inline fun mapSystemSettings(taskType: TaskType.System) = when (taskType) {
    TaskType.System.SimpleIteration -> SystemSimpleIterationSettings()
    TaskType.System.Undefined -> DefaultSystemSettings
}