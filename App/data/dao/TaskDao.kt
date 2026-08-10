package com.antichaos.app.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.antichaos.app.core.domain.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    suspend fun getAll(): List<Task>

    @Query("DELETE FROM tasks")
    suspend fun clearAll()
}