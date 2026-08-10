package com.antichaos.app.data.repository

import androidx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import com.antichaos.app.core.domain.model.Task
import com.antichaos.app.data.dao.TaskDao
import com.antichaos.app.data.database.AntiChaosDatabase
import com.antichaos.app.domain.repository.TaskRepository

class TaskRepositoryImpl(private val database: AntiChaosDatabase) : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    override val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    override suspend fun addTask(task: Task) {
        withContext(Dispatchers.IO) {
            database.taskDao().insert(task)
            refreshTasks()
        }
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            database.taskDao().clearAll()
            _tasks.value = emptyList()
        }
    }

    private fun refreshTasks(): List<Task> {
        val current = database.taskDao().getAll()
        return current
    }
}