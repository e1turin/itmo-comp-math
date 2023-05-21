package io.github.e1turin.output.view.pages.main.model

import io.github.e1turin.output.view.entities.settings.model.UndefinedDefaultSettings
import io.github.e1turin.output.view.entities.task.model.Task
import io.github.e1turin.output.view.entities.task.model.TaskType

class MainPage {
    val task: Task = Task(
        taskType = TaskType.Undefined,
        settings = UndefinedDefaultSettings
    )
}