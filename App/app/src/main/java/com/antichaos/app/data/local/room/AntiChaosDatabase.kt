package com.antichaos.app.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antichaos.app.data.local.entity.TaskEntity
import com.antichaos.app.data.local.entity.TaskStepEntity
import com.antichaos.app.data.local.entity.ReminderEntity
import com.antichaos.app.data.local.entity.RecurringReminderEntity
import com.antichaos.app.data.local.entity.HabitEntity
import com.antichaos.app.data.local.entity.HabitCompletionEntity
import com.antichaos.app.data.local.entity.JournalEntryEntity
import com.antichaos.app.data.local.entity.EveningReviewEntity
import com.antichaos.app.data.local.entity.TechniqueEntity
import com.antichaos.app.data.local.entity.CoachingSessionEntity
import com.antichaos.app.data.local.entity.UserStateLogEntity
import com.antichaos.app.data.local.entity.DailyAnchorEntity
import com.antichaos.app.data.local.entity.LifeAreaEntity
import com.antichaos.app.data.local.entity.BookEntity
import com.antichaos.app.data.local.entity.QuoteEntity
import com.antichaos.app.data.local.entity.MethodologyEntity
import com.antichaos.app.data.local.entity.SavedNoteEntity
import com.antichaos.app.data.local.dao.TaskDao
import com.antichaos.app.data.local.dao.ReminderDao
import com.antichaos.app.data.local.dao.HabitDao
import com.antichaos.app.data.local.dao.JournalDao
import com.antichaos.app.data.local.dao.CoachDao
import com.antichaos.app.data.local.dao.LibraryDao
import com.antichaos.app.data.local.dao.SettingsDao

@Database(
    version = 1,
    exportSchema = false, // Will enable after final schema is stable
    entities = [
        // Tasks module
        TaskEntity::class,
        TaskStepEntity::class,

        // Reminders module
        ReminderEntity::class,
        RecurringReminderEntity::class,

        // Habits module
        HabitEntity::class,
        HabitCompletionEntity::class,

        // Journal module
        JournalEntryEntity::class,
        EveningReviewEntity::class,

        // AI Coach module
        TechniqueEntity::class,
        CoachingSessionEntity::class,
        UserStateLogEntity::class,

        // Daily OS & Life Areas
        DailyAnchorEntity::class,
        LifeAreaEntity::class,

        // Knowledge Base (Phase 2)
        BookEntity::class,
        QuoteEntity::class,
        MethodologyEntity::class,
        SavedNoteEntity::class
    ]
)
abstract class AntiChaosDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun reminderDao(): ReminderDao
    abstract fun habitDao(): HabitDao
    abstract fun journalDao(): JournalDao
    abstract fun coachDao(): CoachDao
    abstract fun libraryDao(): LibraryDao
    abstract fun settingsDao(): SettingsDao
}
