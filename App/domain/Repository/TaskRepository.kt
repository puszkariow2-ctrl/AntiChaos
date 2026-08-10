package com.antichaos.app.domain.repository

import com.antichaos.app.core.domain.model.Task
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val tasks: StateFlow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun clearAll()
}