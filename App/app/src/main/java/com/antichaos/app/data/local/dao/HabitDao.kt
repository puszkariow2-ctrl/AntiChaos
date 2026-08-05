package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.HabitCompletionEntity
import com.antichaos.app.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    // Habits CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits WHERE id = :habitId")
    suspend fun getHabitById(habitId: Long): HabitEntity?

    // Get active habits for today's view
    @Query("SELECT * FROM habits WHERE isActive = 1 ORDER BY title ASC")
    fun getActiveHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE lifeAreaId = :lifeAreaId AND isActive = 1")
    fun getHabitsByLifeArea(lifeAreaId: Long): Flow<List<HabitEntity>>

    // Completions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: HabitCompletionEntity): Long

    @Query("""
        SELECT * FROM habit_completions 
        WHERE habitId = :habitId AND dateEpochDays = :dateEpochDays
    """)
    suspend fun getCompletionForDate(habitId: Long, dateEpochDays: Long): HabitCompletionEntity?

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId ORDER BY completedAtEpochSeconds DESC LIMIT 30")
    suspend fun getRecentCompletions(habitId: Long): List<HabitCompletionEntity>

    // Get today's completions for all habits (for home screen)
    @Query("""
        SELECT habitId FROM habit_completions 
        WHERE dateEpochDays = :todayEpochDays
    """)
    suspend fun getCompletedHabitIdsToday(todayEpochDays: Long): List<Long>

    // Streak calculation helper — get completion dates for a habit range
    @Query("""
        SELECT dateEpochDays FROM habit_completions 
        WHERE habitId = :habitId AND dateEpochDays >= :fromDate AND dateEpochDays <= :toDate
        ORDER BY dateEpochDays DESC
    """)
    suspend fun getCompletionDatesInRange(habitId: Long, fromDate: Long, toDate: Long): List<Long>

    // Stats
    @Query("SELECT COUNT(*) FROM habits WHERE isActive = 1")
    fun getActiveHabitCount(): Flow<Int>

    @Query("""
        SELECT SUM(totalCompletions) FROM habits 
        WHERE createdAtEpochSeconds >= :startOfWeek
    """)
    suspend fun getTotalCompletionsThisWeek(startOfWeek: Long): Int?
}
