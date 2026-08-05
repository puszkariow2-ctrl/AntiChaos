package com.antichaos.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val remindAtEpochSeconds: Long,
    val status: Int = ReminderStatus.SCHEDULED.value,
    val taskId: Long?,           // optional link to task
    val habitId: Long?,          // optional link to habit
    val followUpCount: Int = 0,
    val maxFollowUps: Int = 3,
    val nextFollowUpEpochSeconds: Long?,
    val deliveredAtEpochSeconds: Long?,
    val confirmedByUser: Boolean = false,
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

enum class ReminderStatus(val value: Int) {
    SCHEDULED(0), DELIVERED(1), CONFIRMED(2), SKIPPED(3), CANCELLED(4);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: SCHEDULED
    }
}

@Entity(tableName = "recurring_reminders")
data class RecurringReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val timeOfDayMinutes: Int,           // minutes from midnight (e.g., 645 for 10:45)
    val recurrencePattern: Int = RecurrencePattern.DAILY.value,
    val daysOfWeekMask: Int?,            // bitmask for weekly custom days
    val isActive: Boolean = true,
    val nextRunEpochSeconds: Long,
    val lastRunEpochSeconds: Long?,
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

enum class RecurrencePattern(val value: Int) {
    DAILY(0), WEEKLY_CUSTOM_DAYS(1), WEEKLY_WORKDAYS(2), MONTHLY_SAME_DAY(3);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: DAILY
    }
}
