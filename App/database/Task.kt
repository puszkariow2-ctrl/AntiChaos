package com.antichaos.app.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.InsertOnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val description: String? = null,
    val energyLevel: EnergyLevel = EnergyLevel.MEDIUM,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class EnergyLevel {
    LIGHT (1), MEDIUM (2), HEAVY (3)
}

@Dao
interface TaskDao {
    @Insert(onConflict = InsertOnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Query("SELECT * FROM tasks WHERE isCompleted = :completed ORDER BY createdAt DESC")
    suspend fun getTasksByStatus(completed: Boolean): List<Task>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: String): Task?

    @Query("DELETE FROM tasks")
    suspend fun clearAll()
}

class AntiChaosDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        const val VERSION = 1
    }
}