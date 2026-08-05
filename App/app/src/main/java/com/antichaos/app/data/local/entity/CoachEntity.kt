package com.antichaos.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "techniques")
data class TechniqueEntity(
    @PrimaryKey
    val code: String,                   // unique code like "breathing_478"
    val name: String,                   // display name "Дихання 4-7-8"
    val category: Int = TechniqueCategory.STABILIZATION.value,
    val description: String,            // short: what it is and when it helps
    val stepsJson: String,              // JSON array of step strings
    val durationMinutes: Int?,          // how long it takes
    val triggersJson: String,           // JSON array: ["stress", "insomnia"]
    val sourceBookId: Long?             // which book this technique comes from
)

@Entity(tableName = "coaching_sessions")
data class CoachingSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userMessage: String,            // what the user wrote/said
    val detectedEmotion: Int?,          // Emotion enum value
    val detectedIntensity: Int?,        // 1-5 scale
    val aiResponse: String,             // AI's response text
    val suggestedTechniquesJson: String,// JSON array of technique codes
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

@Entity(tableName = "user_state_logs")
data class UserStateLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestampEpochSeconds: Long = System.currentTimeMillis() / 1000,
    val mood: Int?,                     // Mood enum value
    val energyLevel: Int?,              // 1-10 scale
    val context: String?,               // what was happening
    val triggerType: Int?,              // TriggerType enum value
    val techniqueUsed: String?          // technique code if user applied one
)

enum class TechniqueCategory(val value: Int) {
    STABILIZATION(0), COGNITIVE(1), ACTION(2), EMOTIONAL(3);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: STABILIZATION
    }
}

enum class Emotion(val value: Int) {
    STRESSED(0), OVERWHELMED(1), ANXIOUS(2), SAD(3), MOTIVATED(4),
    FOCUSED(5), TIRED(6), CONFUSED(7), PROUD(8), NEUTRAL(9);

    companion object {
        fun fromValue(value: Int?) = value?.let { entries.find { e -> e.value == it } }
    }
}

enum class TriggerType(val value: Int) {
    STRESS(0), URGE(1), OVERWHELM(2), PROCRASTINATION(3), ANXIETY(4), NONE(5);

    companion object {
        fun fromValue(value: Int?) = value?.let { entries.find { e -> e.value == it } }
    }
}
