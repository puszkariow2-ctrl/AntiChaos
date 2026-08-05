package com.antichaos.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val entryType: Int = JournalEntryType.FREE_WRITE.value,
    val content: String,
    val mood: Int?,                     // Mood enum value
    val energyLevel: Int?,              // 1-10 scale
    val dateEpochDays: Long,            // days since epoch for the entry date
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000,
    val aiSummary: String?,             // AI-generated summary (optional)
    val aiInsightsJson: String?         // JSON array of AI insights
)

@Entity(tableName = "evening_reviews")
data class EveningReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dateEpochDays: Long,            // unique per day
    val wentWellJson: String?,          // JSON array of strings (3 things that went well)
    val wasChallenging: String?,        // what was hard today
    val learnedToday: String?,          // what I learned
    val dayRating: Int?,                // 1-10 scale
    val tomorrowPrioritiesJson: String?,// JSON array of strings (1-3 priorities)
    val completedAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

enum class JournalEntryType(val value: Int) {
    FREE_WRITE(0), MORNING_PAGES(1), EVENING_REVIEW(2), GRATITUDE(3), MOOD_LOG(4);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: FREE_WRITE
    }
}

enum class Mood(val value: Int, val emoji: String) {
    VERY_LOW(0, "🪫"), LOW(1, "😕"), NEUTRAL(2, "😐"), GOOD(3, "🙂"), GREAT(4, "🔥");

    companion object {
        fun fromValue(value: Int?) = value?.let { entries.find { e -> e.value == it } }
    }
}
