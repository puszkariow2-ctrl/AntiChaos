package com.antichaos.app.di

import dagger.Module
import dagger.ModuleExtension
import dagger.provider.Provider
import androidx.room.RoomDatabase
import dagger.hilt.android.inject.AndroidModule
import com.antichaos.app.data.Database.AntiChaosDatabase
import com.antichaos.app.domain.repository.TaskRepository
import com.antichaos.app.data.repository.TaskRepositoryImpl

@Module
@InstallIn(AndroidModule::class)
@ModuleExtension
object AppModule {
    @Provides
    fun provideTaskRepository(): TaskRepository = TaskRepositoryImpl(AntiChaosDatabase())
}