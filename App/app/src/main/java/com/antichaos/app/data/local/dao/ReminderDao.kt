package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.ReminderEntity
import com.antichaos.app.data.local.entity.RecurringReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    // One-time reminders
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Update
    suspend fun updateReminder(reminder: ReminderEntity)

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)

    @Query("SELECT * FROM reminders WHERE id = :id")
    suspend fun getReminderById(id: Long): ReminderEntity?

    // Get upcoming/due reminders (for WorkManager scanning)
    @Query("""
        SELECT * FROM reminders 
        WHERE status = 0 AND remindAtEpochSeconds <= :now 
        ORDER BY remindAtEpochSeconds ASC
        LIMIT 20
    """)
    suspend fun getDueReminders(now: Long): List<ReminderEntity>

    // Get scheduled reminders for display
    @Query("SELECT * FROM reminders WHERE status IN (0, 1) ORDER BY remindAtEpochSeconds ASC")
    fun getScheduledReminders(): Flow<List<ReminderEntity>>

    // Follow-up reminders that need retry
    @Query("""
        SELECT * FROM reminders 
        WHERE status = 1 AND nextFollowUpEpochSeconds IS NOT NULL 
        AND nextFollowUpEpochSeconds <= :now 
        AND followUpCount < maxFollowUps
    """)
    suspend fun getFollowUpReminders(now: Long): List<ReminderEntity>

    // Recurring reminders
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecurringReminder(reminder: RecurringReminderEntity): Long

    @Update
    suspend fun updateRecurringReminder(reminder: RecurringReminderEntity)

    @Delete
    suspend fun deleteRecurringReminder(reminder: RecurringReminderEntity)

    @Query("SELECT * FROM recurring_reminders WHERE isActive = 1 ORDER BY timeOfDayMinutes ASC")
    fun getActiveRecurringReminders(): Flow<List<RecurringReminderEntity>>

    // Get recurring reminders due now (for scheduler)
    @Query("""
        SELECT * FROM recurring_reminders 
        WHERE isActive = 1 AND nextRunEpochSeconds <= :now
    """)
    suspend fun getDueRecurringReminders(now: Long): List<RecurringReminderEntity>

    // Stats
    @Query("SELECT COUNT(*) FROM reminders WHERE status IN (0, 1)")
    fun getScheduledCount(): Flow<Int>
}
