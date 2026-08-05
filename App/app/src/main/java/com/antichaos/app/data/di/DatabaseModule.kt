package com.antichaos.app.data.di

import android.content.Context
import androidx.room.Room
import com.antichaos.app.data.local.room.AntiChaosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAntiChaosDatabase(
        @ApplicationContext context: Context
    ): AntiChaosDatabase {
        return Room.databaseBuilder(
            context,
            AntiChaosDatabase::class.java,
            "antichaos_db"
        )
            .fallbackToDestructiveMigration() // TODO: Replace with proper migrations after schema stabilizes
            .build()
    }

    @Provides
    fun provideTaskDao(database: AntiChaosDatabase) = database.taskDao()

    @Provides
    fun provideReminderDao(database: AntiChaosDatabase) = database.reminderDao()

    @Provides
    fun provideHabitDao(database: AntiChaosDatabase) = database.habitDao()

    @Provides
    fun provideJournalDao(database: AntiChaosDatabase) = database.journalDao()

    @Provides
    fun provideCoachDao(database: AntiChaosDatabase) = database.coachDao()

    @Provides
    fun provideLibraryDao(database: AntiChaosDatabase) = database.libraryDao()

    @Provides
    fun provideSettingsDao(database: AntiChaosDatabase) = database.settingsDao()
}
