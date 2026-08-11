package com.antichaos.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_anchors")
data class DailyAnchorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,                  // e.g., "🌅 Ранковий штаб"
    val timeOfDayMinutes: Int,          // minutes from midnight (e.g., 600 for 10:00)
    val scenarioType: Int = AnchorScenario.MORNING_HQ.value,
    val isActive: Boolean = true,
    val orderIndex: Int,                // display order
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

@Entity(tableName = "life_areas")
data class LifeAreaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,                   // e.g., "Здоров'я тіла"
    val icon: String,                   // emoji like "🏃"
    val description: String?,
    val attentionLevel: Int = 3,        // current attention level 1-5
    val orderIndex: Int,                // display order
    val isActive: Boolean = true,
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

enum class AnchorScenario(val value: Int) {
    MORNING_HQ(0), SPORT_TIME(1), FOCUS_BLOCK(2), MEAL_TIME(3),
    EVENING_TRANSITION(4), EVENING_REVIEW(5);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: MORNING_HQ
    }
}

// Default life areas that will be seeded on first launch
object DefaultLifeAreas {
    val areas = listOf(
        LifeAreaEntity(id = 1, name = "Здоров'я тіла", icon = "🏃", description = null, orderIndex = 0),
        LifeAreaEntity(id = 2, name = "Ментальне здоров'я", icon = "🧠", description = null, orderIndex = 1),
        LifeAreaEntity(id = 3, name = "Кар'єра/Бізнес", icon = "💼", description = null, orderIndex = 2),
        LifeAreaEntity(id = 4, name = "Фінанси", icon = "💰", description = null, orderIndex = 3),
        LifeAreaEntity(id = 5, name = "Стосунки", icon = "👥", description = null, orderIndex = 4),
        LifeAreaEntity(id = 6, name = "Особистий ріст", icon = "📚", description = null, orderIndex = 5),
        LifeAreaEntity(id = 7, name = "Творчість", icon = "🎨", description = null, orderIndex = 6),
        LifeAreaEntity(id = 8, name = "Відпочинок", icon = "🎮", description = null, orderIndex = 7)
    )
}

// Default daily anchors that will be seeded on first launch
object DefaultDailyAnchors {
    val anchors = listOf(
        DailyAnchorEntity(id = 1, title = "🌅 Ранковий штаб", timeOfDayMinutes = 600, orderIndex = 0),
        DailyAnchorEntity(id = 2, title = "💪 Спорт", timeOfDayMinutes = 645, scenarioType = AnchorScenario.SPORT_TIME.value, orderIndex = 1),
        DailyAnchorEntity(id = 3, title = "🎯 Фокус-блок", timeOfDayMinutes = 900, scenarioType = AnchorScenario.FOCUS_BLOCK.value, orderIndex = 2),
        DailyAnchorEntity(id = 4, title = "🍽 Обід", timeOfDayMinutes = 1080, scenarioType = AnchorScenario.MEAL_TIME.value, orderIndex = 3),
        DailyAnchorEntity(id = 5, title = "🌙 Вечірній перехід", timeOfDayMinutes = 1425, scenarioType = AnchorScenario.EVENING_TRANSITION.value, orderIndex = 4),
        DailyAnchorEntity(id = 6, title = "📓 Вечірній звіт", timeOfDayMinutes = 1530, scenarioType = AnchorScenario.EVENING_REVIEW.value, orderIndex = 5)
    )
}
