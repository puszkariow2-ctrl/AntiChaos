package com.antichaos.app.data.repository

import com.antichaos.app.data.local.dao.TaskDao
import com.antichaos.app.domain.model.Task
import com.antichaos.app.domain.model.TaskStep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }

    fun getActiveTasks(): Flow<List<Task>> =
        taskDao.getActiveTasks().map { entities ->
            entities.map { it.toDomain() }
        }

    fun getTasksForToday(todayEpochSeconds: Long): Flow<List<Task>> =
        taskDao.getTasksForToday(todayEpochSeconds).map { entities ->
            entities.map { it.toDomain() }
        }

    suspend fun getTaskById(taskId: Long): Task? =
        taskDao.getTaskById(taskId)?.toDomain()

    suspend fun createTask(task: Task): Long {
        val entity = task.toEntity()
        return taskDao.insertTask(entity)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    suspend fun deleteTask(taskId: Long) {
        taskDao.getTaskById(taskId)?.let { taskDao.deleteTask(it) }
    }

    // Domain <-> Entity mapping (could be moved to separate mappers)
    private fun com.antichaos.app.data.local.entity.TaskEntity.toDomain(): Task {
        return Task(
            id = this.id,
            title = this.title,
            description = this.description,
            projectId = this.projectId,
            lifeAreaId = this.lifeAreaId,
            priority = com.antichaos.app.data.local.entity.Priority.fromValue(this.priority),
            status = com.antichaos.app.data.local.entity.TaskStatus.fromValue(this.status),
            energyLevel = com.antichaos.app.data.local.entity.EnergyLevel.fromValue(this.energyLevel),
            timeEstimateMinutes = this.timeEstimateMinutes,
            dueAtEpochSeconds = this.dueAtEpochSeconds,
            createdAtEpochSeconds = this.createdAtEpochSeconds,
            updatedAtEpochSeconds = this.updatedAtEpochSeconds,
            completedAtEpochSeconds = this.completedAtEpochSeconds
        )
    }

    private fun Task.toEntity(): com.antichaos.app.data.local.entity.TaskEntity {
        return com.antichaos.app.data.local.entity.TaskEntity(
            id = this.id,
            title = this.title,
            description = this.description,
            projectId = this.projectId,
            lifeAreaId = this.lifeAreaId,
            priority = this.priority.value,
            status = this.status.value,
            energyLevel = this.energyLevel?.value,
            timeEstimateMinutes = this.timeEstimateMinutes,
            dueAtEpochSeconds = this.dueAtEpochSeconds,
            createdAtEpochSeconds = this.createdAtEpochSeconds,
            updatedAtEpochSeconds = System.currentTimeMillis() / 1000,
            completedAtEpochSeconds = this.completedAtEpochSeconds
        )
    }
}
