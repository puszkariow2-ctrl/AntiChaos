package com.antichaos.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practices")
data class PracticeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: Int,              // 0=breathing, 1=grounding, 2=physical, 3=mental, 4=emotional, 5=spiritual
    val durationSeconds: Long,
    val difficulty: Int,            // 0=easy, 1=medium, 2=hard
    val instructions: String,
    val whenToUse: String,          // comma-separated tags: "anxiety,focus,sleep"
    val isFavorite: Boolean = false
)

@Entity(tableName = "practice_sessions")
data class PracticeSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val practiceId: Long,
    val completedAtEpochSeconds: Long,
    val moodBefore: Int? = null,
    val moodAfter: Int? = null,
    val notes: String? = null
)

enum class PracticeCategory(val value: Int, val label: String) {
    BREATHING(0, "Дихальні"),
    GROUNDING(1, "Заземлення"),
    PHYSICAL(2, "Тілесні"),
    MENTAL(3, "Ментальні"),
    EMOTIONAL(4, "Емоційні"),
    SPIRITUAL(5, "Духовні/Сенсові")
}

enum class PracticeDifficulty(val value: Int, val label: String) {
    EASY(0, "Легка"),
    MEDIUM(1, "Середня"),
    HARD(2, "Важка")
}
