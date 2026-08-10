package com.antichaos.app.di

import androidx.room.Room
import dagger.Module
import dagger.ModuleExtension
import dagger.provider.Provider
import com.antichaos.app.database.AntiChaosDatabase
import com.antichaos.app.repository.TaskRepositoryImpl

@Module
@InstallIn(AnnotatedComponent::class)
@ModuleExtension
object DatabaseModule {
    @Provides
    fun provideRoomDatabase(): RoomDatabase = AntiChaosDatabase()
}

@Module
class TaskRepositoryModule {
    @Provides
    fun provideTaskDao(database: RoomDatabase): com.antichaos.app.database.TaskDao = database.taskDao()

    @Provides
    fun provideTaskRepository(taskDao: com.antichaos.app.database.TaskDao) : com.antichaos.app.repository.TaskRepositoryImpl {
        return TaskRepositoryImpl() // Simplified for MVP
    }
}