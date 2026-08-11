package com.antichaos.app.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String?,
    val projectId: Long?,
    val lifeAreaId: Long?,
    val priority: Int = Priority.NORMAL.value,        // stored as int enum
    val status: Int = TaskStatus.PLANNED.value,       // stored as int enum
    val energyLevel: Int? = null,                     // EASY/MEDIUM/HARD or null
    val timeEstimateMinutes: Int?,
    val dueAtEpochSeconds: Long?,
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000,
    val updatedAtEpochSeconds: Long = System.currentTimeMillis() / 1000,
    val completedAtEpochSeconds: Long?
)

data class TaskWithSteps(
    @Embedded
    val task: TaskEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val steps: List<TaskStepEntity>
)

enum class TaskStatus(val value: Int) {
    PLANNED(0),       // 📝 заплановано
    IN_PROGRESS(1),   // 🔥 в процесі
    POSTPONED(2),     // ⏸ відкладено
    DONE(3);          // ✅ виконано

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: PLANNED
    }
}

enum class Priority(val value: Int) {
    LOW(0), NORMAL(1), HIGH(2), CRITICAL(3);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: NORMAL
    }
}

enum class EnergyLevel(val value: Int) {
    EASY(1), MEDIUM(2), HARD(3);

    companion object {
        fun fromValue(value: Int?) = value?.let { entries.find { e -> e.value == it } }
    }
}
