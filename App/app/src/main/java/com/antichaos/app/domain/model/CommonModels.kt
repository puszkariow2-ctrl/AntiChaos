package com.antichaos.app.domain.model

import com.antichaos.app.data.local.entity.*

// Habit domain model
data class Habit(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val frequency: HabitFrequency = HabitFrequency.DAILY,
    val daysOfWeekMask: Int?,
    val targetTimeMinutes: Int?,
    val durationMinutes: Int?,
    val difficulty: HabitDifficulty = HabitDifficulty.EASY,
    val stackAfterHabitId: Long?,
    val lifeAreaId: Long?,
    val isActive: Boolean = true,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalCompletions: Int = 0
)

data class HabitCompletion(
    val id: Long = 0,
    val habitId: Long,
    val completedAtEpochSeconds: Long,
    val dateEpochDays: Long,
    val note: String?,
    val moodAfter: Mood?
)

// Journal domain model
data class JournalEntry(
    val id: Long = 0,
    val entryType: JournalEntryType = JournalEntryType.FREE_WRITE,
    val content: String,
    val mood: Mood?,
    val energyLevel: Int?, // 1-10
    val dateEpochDays: Long,
    val createdAtEpochSeconds: Long,
    val aiSummary: String?,
    val aiInsights: List<String>?
)

data class EveningReview(
    val id: Long = 0,
    val dateEpochDays: Long,
    val wentWell: List<String>?,
    val wasChallenging: String?,
    val learnedToday: String?,
    val dayRating: Int?, // 1-10
    val tomorrowPriorities: List<String>?
)

// Reminder domain model
data class Reminder(
    val id: Long = 0,
    val text: String,
    val remindAtEpochSeconds: Long,
    val status: ReminderStatus = ReminderStatus.SCHEDULED,
    val taskId: Long?,
    val habitId: Long?
)

// Coach domain model
data class Technique(
    val code: String,
    val name: String,
    val category: TechniqueCategory,
    val description: String,
    val steps: List<String>,
    val durationMinutes: Int?,
    val triggers: List<String>
)

data class CoachingSession(
    val id: Long = 0,
    val userMessage: String,
    val detectedEmotion: Emotion?,
    val aiResponse: String,
    val suggestedTechniques: List<String>, // technique codes
    val createdAtEpochSeconds: Long
)

// Library domain model
data class Book(
    val id: Long = 0,
    val title: String,
    val author: String,
    val category: String,
    val description: String,
    val keyIdeas: List<String>,
    val practicalExercises: List<String>,
    val isReadByUser: Boolean = false
)

data class Quote(
    val id: Long = 0,
    val text: String,
    val author: String,
    val source: String?,
    val categories: List<String>,
    val isFavorite: Boolean = false
)

// System domain model
data class DailyAnchor(
    val id: Long = 0,
    val title: String,
    val timeOfDayMinutes: Int,
    val scenarioType: AnchorScenario,
    val isActive: Boolean = true
)

data class LifeArea(
    val id: Long = 0,
    val name: String,
    val icon: String,
    val attentionLevel: Int = 3, // 1-5
    val orderIndex: Int
)
