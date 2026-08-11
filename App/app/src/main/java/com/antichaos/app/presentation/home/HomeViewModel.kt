package com.antichaos.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.antichaos.app.domain.model.Task
import com.antichaos.app.data.repository.TaskRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    // ─── Tasks ──────────────────────────────────────────────
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect { list ->
                _tasks.value = list.filter { it.status != com.antichaos.app.data.local.entity.TaskStatus.DONE }
            }
        }
    }

    fun addTask(title: String, description: String? = null) {
        viewModelScope.launch {
            val now = System.currentTimeMillis() / 1000
            val task = Task(
                title = title,
                description = description,
                projectId = null,
                lifeAreaId = null,
                energyLevel = null,
                timeEstimateMinutes = null,
                dueAtEpochSeconds = null,
                createdAtEpochSeconds = now,
                updatedAtEpochSeconds = now,
                completedAtEpochSeconds = null
            )
            taskRepository.createTask(task)
        }
    }

    fun toggleTaskStatus(id: Long) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(id) ?: return@launch
            val newStatus = if (task.status == com.antichaos.app.data.local.entity.TaskStatus.DONE) {
                com.antichaos.app.data.local.entity.TaskStatus.PLANNED
            } else {
                com.antichaos.app.data.local.entity.TaskStatus.DONE
            }
            taskRepository.updateTask(task.copy(status = newStatus))
        }
    }

    // ─── Mood / Greeting ────────────────────────────────────
    private val _selectedMood = MutableStateFlow<Int?>(null)
    val selectedMood: StateFlow<Int?> = _selectedMood.asStateFlow()

    fun selectMood(moodIndex: Int) {
        _selectedMood.value = moodIndex
        // TODO: Save to user_state_logs via repository
    }

    private val _greeting = MutableStateFlow(getGreeting())
    val greeting: StateFlow<String> = _greeting.asStateFlow()

    fun getGreeting(): String {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 4..11 -> "Ранок 👋"
            in 12..17 -> "День ☀️"
            in 18..22 -> "Вечір 🌙"
            else -> "Ніч ✨"
        }
    }

    // ─── Journal Prompt (time-based) ────────────────────────
    fun getJournalPrompt(): String {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> "Що в голові зараз? Запиши за 30 секунд."
            in 12..17 -> "Як проходить день? Що важливе зробив?"
            in 18..23 -> "Швидкий огляд: що пішло добре сьогодні?"
            else -> "Не можеш заснути? Випиши думки на папір."
        }
    }

    // ─── Coach Message (mood-aware) ─────────────────────────
    fun getCoachMessage(): String {
        return when (_selectedMood.value) {
            0 -> "Бачу, ти в гарному настрої! Хочеш зафіксувати що допомогло?"
            1 -> "Нормальний день — і це ок. Щось хочеш обговорити?"
            2 -> "Не найкращий настрій. Можу запропонувати техніку або просто вислухати."
            3 -> "Тривога? Давай подихаємо разом або розберемо що турбує."
            4 -> "Енергія зашкалює! Чому б не використати її на важливе завдання?"
            else -> "Щось турбує? Можу допомогти."
        }
    }

    // ─── Random Action Stats (optional tracking) ────────────
    private val _completedActions = MutableStateFlow(0)
    val completedActions: StateFlow<Int> = _completedActions.asStateFlow()

    fun incrementCompletedAction() {
        _completedActions.value++
    }
}
