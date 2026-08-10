package com.antichaos.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.antichaos.app.database.Task
import com.antichaos.app.repository.TaskRepository
import com.antichaos.app.repository.TaskRepositoryImpl

class MainViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    var tasks by _tasks.asStateFlow()

    // Для Quick Capture: хранить неопределені записи (inbox)
    private val _pendingCaptures = MutableStateFlow<List<PendingCapture>>(emptyList())
    var pendingCaptures by _pendingCaptures.asStateFlow()

    fun addTask(title: String, description: String? = null, energyLevel: com.antichaos.app.database.EnergyLevel = com.antichaos.app.database.EnergyLevel.MEDIUM) {
        viewModelScope.launch {
            val task = Task(
                id = java.util.UUID.randomUUID().toString(),
                title = title,
                description = description,
                energyLevel = energyLevel
            )
            repository.addTask(task)
            refreshTasks()
        }
    }

    fun toggleTaskStatus(id: String) {
        viewModelScope.launch {
            // Упрощено для MVP
            refreshTasks()
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
            _tasks.value = emptyList()
            _pendingCaptures.value = emptyList()
        }
    }

    fun addPendingCapture(text: String) {
        viewModelScope.launch {
            val entry = PendingCapture(
                id = java.util.UUID.randomUUID().toString(),
                text = text,
                timestamp = System.currentTimeMillis()
            )
            _pendingCaptures.value = _pendingCaptures.value + listOf(entry)
        }
    }

    private fun refreshTasks() {
        val current = repository.tasks.firstOrNull().orElse(List()) // Упрощено для MVP
        _tasks.value = current
    }

    data class PendingCapture(
        val id: String,
        val text: String,
        val timestamp: Long
    )
}