# AntiChaos — Модель Даних

## Огляд
Локальна база даних (Room/SQLite) для Android додатку. Offline-first архітектура.

---

## 1. CORE ENITIES

### User Profile
```kotlin
data class UserProfile(
    val id: Long = 1L,              // завжди 1 (single-user app)
    val name: String?,              // ім'я користувача
    val timezone: String = "Europe/Kyiv",
    val onboardingCompleted: Boolean = false,
    val preferredStartHour: Int = 8,
    val preferredEndHour: Int = 23,
    val aiEnabled: Boolean = true,
    val createdAt: Instant,
    val updatedAt: Instant
)
```

---

## 2. TASKS SYSTEM

### Task
```kotlin
data class Task(
    val id: Long,
    val title: String,
    val description: String?,
    val projectId: Long?,           // посилання на Project
    val lifeAreaId: Long?,          // посилання на LifeArea
    val priority: Priority = Priority.NORMAL,  // LOW / NORMAL / HIGH / CRITICAL
    val status: TaskStatus = TaskStatus.PLANNED,
    val energyLevel: EnergyLevel? = null,      // EASY / MEDIUM / HARD
    val timeEstimateMinutes: Int?,
    val dueAt: Instant?,
    val steps: List<TaskStep>,       // embedded або окрема таблиця
    val createdAt: Instant,
    val updatedAt: Instant,
    val completedAt: Instant?
)

enum class TaskStatus {
    PLANNED,        // 📝 заплановано
    IN_PROGRESS,    // 🔥 в процесі
    POSTPONED,      // ⏸ відкладено
    DONE            // ✅ виконано
}

enum class Priority {
    LOW, NORMAL, HIGH, CRITICAL
}

enum class EnergyLevel {
    EASY, MEDIUM, HARD
}
```

### Task Step
```kotlin
data class TaskStep(
    val id: Long,
    val taskId: Long,
    val title: String,
    val completed: Boolean = false,
    val orderIndex: Int,
    val createdAt: Instant
)
```

---

## 3. REMINDERS SYSTEM

### Reminder
```kotlin
data class Reminder(
    val id: Long,
    val text: String,
    val remindAt: Instant,
    val status: ReminderStatus = ReminderStatus.SCHEDULED,
    val taskId: Long?,               // опціонально прив'язка до задачі
    val habitId: Long?,              // опціонально прив'язка до звички
    val followUpCount: Int = 0,
    val maxFollowUps: Int = 3,
    val nextFollowUpAt: Instant?,
    val deliveredAt: Instant?,
    val confirmedByUser: Boolean = false,
    val createdAt: Instant
)

enum class ReminderStatus {
    SCHEDULED, DELIVERED, CONFIRMED, SKIPPED, CANCELLED
}
```

### RecurringReminder
```kotlin
data class RecurringReminder(
    val id: Long,
    val title: String,
    val timeOfDay: LocalTime,        // наприклад 10:45
    val recurrencePattern: RecurrencePattern,
    val daysOfWeek: Set<DayOfWeek>?, // для weekly pattern
    val isActive: Boolean = true,
    val nextRunAt: Instant,
    val lastRunAt: Instant?,
    val createdAt: Instant
)

enum class RecurrencePattern {
    DAILY, WEEKLY_CUSTOM_DAYS, WEEKLY_WORKDAYS, MONTHLY_SAME_DAY
}
```

---

## 4. HABITS SYSTEM

### Habit
```kotlin
data class Habit(
    val id: Long,
    val title: String,
    val description: String?,
    val frequency: HabitFrequency,
    val daysOfWeek: Set<DayOfWeek>?, // для non-daily habits
    val targetTime: LocalTime?,      // бажаний час (опціонально)
    val durationMinutes: Int?,       // очікувана тривалість
    val difficulty: HabitDifficulty = HabitDifficulty.EASY,
    val stackAfterHabitId: Long?,    // habit stacking
    val lifeAreaId: Long?,           // до якої сфери належить
    val isActive: Boolean = true,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val totalCompletions: Int = 0,
    val createdAt: Instant,
    val updatedAt: Instant
)

enum class HabitFrequency {
    DAILY,
    WEEKLY_CUSTOM_DAYS,
    WEEKLY_WORKDAYS,
    CUSTOM_INTERVAL       // наприклад "кожні 2 дні"
}

enum class HabitDifficulty {
    EASY, MEDIUM, HARD
}
```

### HabitCompletion
```kotlin
data class HabitCompletion(
    val id: Long,
    val habitId: Long,
    val completedAt: Instant,        // коли відмічено
    val date: LocalDate,              // для якої дати (може відрізнятися через час)
    val note: String?,                // опціональний коментар
    val moodAfter: Mood?              // настрій після виконання
)
```

### MissedHabitDay
```kotlin
data class MissedHabitDay(
    val id: Long,
    val habitId: Long,
    val date: LocalDate,
    val wasIntentional: Boolean = false,  // свідомо пропущено чи забув?
    val reason: String?,
    val createdAt: Instant
)
```

---

## 5. JOURNAL SYSTEM

### JournalEntry
```kotlin
data class JournalEntry(
    val id: Long,
    val entryType: JournalEntryType,
    val content: String,              // основний текст
    val mood: Mood?,                  // настрій під час запису
    val energyLevel: Int?,            // 1-10
    val date: LocalDate,              // дата запису (зазвичай today)
    val createdAt: Instant,
    val aiSummary: String?,           // AI-generated summary
    val aiInsights: List<String>?     // AI-generated insights
)

enum class JournalEntryType {
    FREE_WRITE,        // вільний текст
    MORNING_PAGES,     // ранкові сторінки
    EVENING_REVIEW,    // вечірній огляд (шаблон)
    GRATITUDE,         // gratitude journal
    MOOD_LOG           // просто запис настрою з контекстом
}

enum class Mood {
    VERY_LOW, LOW, NEUTRAL, GOOD, GREAT
}
```

### EveningReviewAnswers
```kotlin
data class EveningReview(
    val id: Long,
    val date: LocalDate,
    val wentWell: List<String>,      // що вдалося (3 речі)
    val wasChallenging: String?,     // що було складно
    val learnedToday: String?,       // чому навчився
    val dayRating: Int?,             // 1-10
    val tomorrowPriorities: List<String>, // 1-3 пріоритети на завтра
    val completedAt: Instant
)
```

---

## 6. AI COACH SYSTEM

### CoachingSession
```kotlin
data class CoachingSession(
    val id: Long,
    val userMessage: String,         // що написав користувач
    val detectedState: EmotionalState?, // розпізнаний стан
    val aiResponse: String,          // відповідь AI
    val suggestedTechniques: List<TechniqueReference>,
    val createdAt: Instant
)

data class EmotionalState(
    val primaryEmotion: Emotion,     // STRESSED / OVERWHELMED / MOTIVATED тощо
    val intensity: Int,              // 1-5
    val detectedPatterns: List<String> // "procrastination_loop", "self_criticism"
)

enum class Emotion {
    STRESSED, OVERWHELMED, ANXIOUS, SAD, MOTIVATED, FOCUSED, TIRED, CONFUSED, PROUD, NEUTRAL
}
```

### Technique (Master List — затверджується окремо)
```kotlin
data class Technique(
    val id: Long,
    val code: String,                // унікальний код "breathing_478"
    val name: String,                // "Дихання 4-7-8"
    val category: TechniqueCategory,
    val description: String,         // коротко що це і коли допомагає
    val steps: List<String>,         // покрокова інструкція
    val durationMinutes: Int?,       // скільки часу займає
    val triggers: List<String>,      // коли застосовувати ["stress", "insomnia"]
    val sourceBookId: Long?          // з якої книги/джерела
)

enum class TechniqueCategory {
    STABILIZATION,     // дихання, заземлення
    COGNITIVE,         // рефреймінг, когнітивні техніки
    ACTION,            // для прокрастинації та дії
    EMOTIONAL          // робота з емоціями
}
```

### UserStateLog
```kotlin
data class UserStateLog(
    val id: Long,
    val timestamp: Instant,
    val mood: Mood?,
    val energyLevel: Int?,           // 1-10
    val context: String?,            // що відбувалося ("перед важливою зустріччю")
    val triggerType: TriggerType?,   // тип тригера якщо був
    val techniqueUsed: String?       // яку техніку застосував (код)
)

enum class TriggerType {
    STRESS, URGE, OVERWHELM, PROCRASTINATION, ANXIETY, NONE
}
```

---

## 7. KNOWLEDGE BASE

### Book
```kotlin
data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val category: String,            // "productivity", "psychology", "habits"
    val description: String,         // 2-3 речення про книгу
    val keyIdeas: List<String>,      // 5-10 ключових ідей
    val practicalExercises: List<String>, // вправи з книги
    val isReadByUser: Boolean = false,
    val userNotes: String?,          // нотатки користувача
    val createdAt: Instant           // коли додано в бібліотеку
)
```

### Quote
```kotlin
data class Quote(
    val id: Long,
    val text: String,
    val author: String,
    val source: String?,             // книга/виступ
    val categories: List<String>,    // ["motivation", "discipline"]
    val isFavorite: Boolean = false,
    val appliedToTaskId: Long?       // до якої задачі/звички застосував
)
```

### Methodology
```kotlin
data class Methodology(
    val id: Long,
    val name: String,                // "Pomodoro Technique"
    val description: String,         // що це і для чого
    val steps: List<String>,         // як застосовувати
    val whenToUse: List<String>,     // тригери використання
    val sourceBookId: Long?,         // з якої книги походить
    val relatedTechniqueIds: List<Long>
)
```

### SavedNote (користувацькі нотатки)
```kotlin
data class SavedNote(
    val id: Long,
    val title: String,
    val content: String,
    val tags: List<String>,
    val sourceType: NoteSourceType?, // BOOK / QUOTE / OWN_THOUGHT / AI_INSIGHT
    val createdAt: Instant,
    val updatedAt: Instant
)

enum class NoteSourceType {
    BOOK, QUOTE, OWN_THOUGHT, AI_INSIGHT, EXTERNAL_LINK
}
```

---

## 8. LIFE AREAS SYSTEM

### LifeArea
```kotlin
data class LifeArea(
    val id: Long,
    val name: String,                // "Здоров'я тіла", "Кар'єра"
    val icon: String,                // emoji або ресурс
    val description: String?,
    val attentionLevel: Int = 3,     // поточний рівень уваги 1-5
    val orderIndex: Int,             // порядок відображення
    val isActive: Boolean = true,
    val createdAt: Instant
)
```

Default Life Areas:
1. 🏃 Здоров'я тіла
2. 🧠 Ментальне здоров'я
3. 💼 Кар'єра/Бізнес
4. 💰 Фінанси
5. 👥 Стосунки
6. 📚 Особистий ріст
7. 🎨 Творчість
8. 🎮 Відпочинок

---

## 9. DAILY OS / ANCHORS

### DailyAnchor
```kotlin
data class DailyAnchor(
    val id: Long,
    val title: String,               // "🌅 Ранковий штаб"
    val timeOfDay: LocalTime,        // 10:00
    val scenarioType: AnchorScenario,// MORNING_HQ / FOCUS_BLOCK тощо
    val isActive: Boolean = true,
    val orderIndex: Int,
    val createdAt: Instant
)

enum class AnchorScenario {
    MORNING_HQ,           // ранковий штаб + вибір стану
    SPORT_TIME,           // час для спорту
    FOCUS_BLOCK,          // фокус-блок для важливих задач
    MEAL_TIME,            // прийом їжі (pause moment)
    EVENING_TRANSITION,   // перехід у вечірній режим
    EVENING_REVIEW        // вечірній звіт
}
```

---

## 10. PROGRESS & ANALYTICS

### DailySummary
```kotlin
data class DailySummary(
    val id: Long,
    val date: LocalDate,
    val tasksCompleted: Int,
    val habitsCompleted: List<Long>, // habit IDs completed today
    val journalEntryCount: Int,
    val moodAverage: Float?,         // середній настрій за день
    val dayRating: Int?,             // з evening review (1-10)
    val notes: String?
)
```

### WeeklyInsight
```kotlin
data class WeeklyInsight(
    val id: Long,
    val weekStartDate: LocalDate,
    val tasksCompletedTotal: Int,
    val habitConsistencyRate: Float, // 0.0 - 1.0
    val bestDay: LocalDate?,         // найкращий день тижня
    val worstDay: LocalDate?,        // найскладніший день
    val aiGeneratedInsights: List<String>, // персональні інсайти
    val lifeAreaAttention: Map<Long, Int>, // attention per area
    val createdAt: Instant
)
```

### Achievement
```kotlin
data class Achievement(
    val id: Long,
    val code: String,                // "first_week_habit"
    val title: String,               // "Перший тиждень!"
    val description: String,         // "Ти тримав звичку 7 днів поспіль"
    val icon: String,
    val unlockedAt: Instant?,        // null = not yet unlocked
    val criteriaMet: Boolean = false
)
```

---

## 11. SETTINGS & PREFERENCES

### UserPreferences
```kotlin
data class UserPreference(
    val key: String,                 // "notifications_enabled", "dark_mode"
    val value: String,               // JSON-serialized value
    val category: PreferenceCategory
)

enum class PreferenceCategory {
    NOTIFICATIONS, APPEARANCE, AI_SETTINGS, PRIVACY, ADVANCED
}
```

---

## Підсумок Таблиць (Room Entities)

| Entity | Призначення | Критичність для MVP |
|--------|-------------|---------------------|
| UserProfile | Дані користувача | ✅ Must |
| Task, TaskStep | Система задач | ✅ Must |
| Reminder, RecurringReminder | Нагадування | ✅ Must |
| Habit, HabitCompletion, MissedHabitDay | Трекер звичок | ✅ Must |
| JournalEntry, EveningReview | Щоденник | ✅ Must |
| Technique, CoachingSession | AI психолог | ✅ Must |
| UserStateLog | Логування стану | ✅ Must |
| LifeArea | Сфери життя | ⚠️ Should (Phase 2) |
| Book, Quote, Methodology, SavedNote | Knowledge Base | ⚠️ Should (Phase 2) |
| DailyAnchor | Daily OS | ✅ Must |
| DailySummary, WeeklyInsight | Аналітика | ⚠️ Should (Phase 2) |
| Achievement | Досягнення | 🟢 Nice to Have |

**MVP таблиці: ~14 основних entity**
