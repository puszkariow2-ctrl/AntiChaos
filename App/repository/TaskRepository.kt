package com.antichaos.app.repository

import com.antichaos.app.database.AntiChaosDatabase
import com.antichaos.app.database.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

interface TaskRepository {
    val tasks: Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun toggleTaskStatus(id: String)
    suspend fun clearAll()
}

class TaskRepositoryImpl : TaskRepository {
    private val database = AntiChaosDatabase()
    private val _tasks = MutableStateFlow(List<Task>())

    override val tasks: Flow<List<Task>> = _tasks.asStateFlow()

    override suspend fun addTask(task: Task) {
        withContext(androidx.room Room.KT CoroutineDispatcher) {
            database.taskDao().insert(task)
            refreshTasks()
        }
    }

    override suspend fun toggleTaskStatus(id: String) {
        withContext(androidx.room Room.KT CoroutineDispatcher) {
            val task = database.taskDao().getTaskById(id) ?: return@suspend
            // Toggle logic would go here, simplified for MVP
            refreshTasks()
        }
    }

    override suspend fun clearAll() {
        withContext(androidx.room Room.KT CoroutineDispatcher) {
            database.taskDao().clearAll()
            _tasks.value = emptyList()
        }
    }

    private fun refreshTasks() {
        val current = database.taskDao().getTasksByStatus(completed = false)
        _tasks.value = current + database.taskDao().getTasksByStatus(completed = true)
    }
}

fun provideTaskRepository(): TaskRepository = TaskRepositoryImpl()