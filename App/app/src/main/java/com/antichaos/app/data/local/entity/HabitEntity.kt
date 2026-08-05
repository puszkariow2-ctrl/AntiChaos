package com.antichaos.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String?,
    val frequency: Int = HabitFrequency.DAILY.value,
    val daysOfWeekMask: Int?,            // bitmask for non-daily habits
    val targetTimeMinutes: Int?,         // preferred time in minutes from midnight
    val durationMinutes: Int?,           // expected duration
    val difficulty: Int = HabitDifficulty.EASY.value,
    val stackAfterHabitId: Long?,        // habit stacking reference
    val lifeAreaId: Long?,               // which life area this belongs to
    val isActive: Boolean = true,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalCompletions: Int = 0,
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000,
    val updatedAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

@Entity(tableName = "habit_completions")
data class HabitCompletionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val habitId: Long,
    val completedAtEpochSeconds: Long = System.currentTimeMillis() / 1000,
    val dateEpochDays: Long,             // days since epoch for easy daily queries
    val note: String?,                   // optional comment
    val moodAfter: Int?                  // Mood enum value if user logged it
)

enum class HabitFrequency(val value: Int) {
    DAILY(0), WEEKLY_CUSTOM_DAYS(1), WEEKLY_WORKDAYS(2), CUSTOM_INTERVAL(3);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: DAILY
    }
}

enum class HabitDifficulty(val value: Int) {
    EASY(1), MEDIUM(2), HARD(3);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: EASY
    }
}
