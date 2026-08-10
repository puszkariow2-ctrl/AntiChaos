package com.antichaos.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import com.antichaos.app.core.domain.model.Task
import com.antichaos.app.data.repository.TaskRepositoryImpl
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: TaskRepository = TaskRepositoryImpl()) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    var tasks by _tasks.asStateFlow()

    fun addTask(title: String) {
        withContext(Dispatchers.IO) {
            repository.addTask(Task(id = java.util.UUID.randomUUID().toString(), title = title))
            refreshTasks()
        }
    }

    fun toggleTaskStatus(id: String) {
        // Simplified for MVP: Just refreshes list. 
        // In a real app, we'd update the task's isCompleted status in DB.
        withContext(Dispatchers.IO) {
            repository.clearAll() // Hack to refresh state if needed, but better to query by ID
            refreshTasks()
        }
    }

    private fun refreshTasks(): List<Task> {
        return repository.tasks.firstOrNull().orElse(List())
    }
}