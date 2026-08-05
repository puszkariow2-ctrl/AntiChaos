package com.antichaos.app.domain.model

import com.antichaos.app.data.local.entity.EnergyLevel
import com.antichaos.app.data.local.entity.Priority
import com.antichaos.app.data.local.entity.TaskStatus

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val projectId: Long?,
    val lifeAreaId: Long?,
    val priority: Priority = Priority.NORMAL,
    val status: TaskStatus = TaskStatus.PLANNED,
    val energyLevel: EnergyLevel?,
    val timeEstimateMinutes: Int?,
    val dueAtEpochSeconds: Long?,
    val createdAtEpochSeconds: Long,
    val updatedAtEpochSeconds: Long,
    val completedAtEpochSeconds: Long?
)

data class TaskStep(
    val id: Long = 0,
    val taskId: Long,
    val title: String,
    val completed: Boolean = false,
    val orderIndex: Int
)
