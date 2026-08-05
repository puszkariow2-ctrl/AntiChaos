package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.TaskEntity
import com.antichaos.app.data.local.entity.TaskStepEntity
import com.antichaos.app.data.local.entity.TaskWithSteps
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity?

    @Query("SELECT * FROM tasks ORDER BY updatedAtEpochSeconds DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    // Filtered queries
    @Query("""
        SELECT * FROM tasks 
        WHERE status != 3  -- not DONE
        ORDER BY priority DESC, dueAtEpochSeconds ASC
    """)
    fun getActiveTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE dueAtEpochSeconds IS NOT NULL AND dueAtEpochSeconds <= :now AND status != 3")
    suspend fun getOverdueTasks(now: Long): List<TaskEntity>

    @Query("""
        SELECT * FROM tasks 
        WHERE date(dueAtEpochSeconds / 1000, 'unixepoch') = date(:todayEpochSeconds / 1000, 'unixepoch')
        AND status != 3
        ORDER BY priority DESC
    """)
    fun getTasksForToday(todayEpochSeconds: Long): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE lifeAreaId = :lifeAreaId AND status != 3")
    fun getTasksByLifeArea(lifeAreaId: Long): Flow<List<TaskEntity>>

    // Task with steps
    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskWithSteps(taskId: Long): TaskWithSteps?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: TaskStepEntity): Long

    @Update
    suspend fun updateStep(step: TaskStepEntity)

    @Delete
    suspend fun deleteStep(step: TaskStepEntity)

    // Stats
    @Query("SELECT COUNT(*) FROM tasks WHERE status = 3 AND completedAtEpochSeconds >= :startOfDay")
    suspend fun getCompletedCountToday(startOfDay: Long): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE status != 3")
    fun getActiveTaskCount(): Flow<Int>
}
