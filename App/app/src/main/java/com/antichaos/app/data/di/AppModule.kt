package com.antichaos.app.data.di

import android.content.Context
import androidx.work.WorkManager
import com.antichaos.app.core.ai.AiCoachService
import com.antichaos.app.core.db.DatabaseSeeder
import com.antichaos.app.core.nlp.NaturalLanguageParser
import com.antichaos.app.core.notifications.ReminderNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideNaturalLanguageParser(): NaturalLanguageParser {
        return NaturalLanguageParser()
    }

    @Singleton
    @Provides
    fun provideAiCoachService(
        coachDao: com.antichaos.app.data.local.dao.CoachDao
    ): AiCoachService {
        return AiCoachService(coachDao)
    }

    @Singleton
    @Provides
    fun provideDatabaseSeeder(
        settingsDao: com.antichaos.app.data.local.dao.SettingsDao,
        coachDao: com.antichaos.app.data.local.dao.CoachDao
    ): DatabaseSeeder {
        return DatabaseSeeder(settingsDao, coachDao)
    }

    // ReminderNotificationManager provided via Hilt-assisted injection with WorkManager
}
