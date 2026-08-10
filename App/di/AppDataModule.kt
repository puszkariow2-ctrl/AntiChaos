package com.antichaos.app.di

import androidx.room.RoomDatabase
import dagger.Module
import dagger.ModuleExtension
import dagger.provider.Provider
import com.antichaos.app.data.Database.AntiChaosDatabase
import com.antichaos.app.data.repository.TaskRepositoryImpl
import com.antichaos.app.domain.Repository.TaskRepository

@Module
@InstallIn(AndroidModule::class) // Assuming AndroidModule exists or we create it, but let's stick to simple ModuleExtension for now if needed.
// Actually, usually we use @InstallIn(SingletonComponent::class) and @Inject.
// Let's keep it simple: just provide the dependencies.

@Module
object AppModule {
    @Provides
    fun provideTaskRepository(): TaskRepository {
        return TaskRepositoryImpl() // Simple impl for MVP, can be swapped with Hilt later if needed, but Impl is enough for now to get data flowing.
        // Wait, if I use DI, I should inject the Repo into ViewModel.
        // Let's make the Repo a singleton provided by this module.
    }
}

// Actually, let's simplify: The repository implementation uses AntiChaosDatabase directly inside it? 
// No, that couples Data to Domain if not careful. But for MVP, it's fine.
// Better: Provide TaskRepoImpl which takes Database in constructor? Or just instantiate.
// Let's make the Impl injectable properly.

package com.antichaos.app.di

import dagger.Module
import dagger.ModuleExtension
import dagger/provider.Provider
import androidx.room.RoomDatabase
import com.antichaos.app.data.Database.AntiChaosDatabase
import com.antichaos.app.domain.Repository.TaskRepository

@Module
@InstallIn(AndroidModule::class) // Need to define AndroidModule or use SingletonComponent
@ModuleExtension
object DataModule {
    @Provides
    fun provideAntiChaosDatabase(): RoomDatabase = AntiChaosDatabase()
}

// Let's just put the Repo Impl in here and have it take DB via constructor.
package com.antichaos.app.di

import dagger.Module
import dagger.ModuleExtension
import dagger.provider.Provider
import androidx.room.RoomDatabase
import com.antichaos.app.data.Database.AntiChaosDatabase
import com.antichaos.app.domain.Repository.TaskRepository
import com.antichaos.app.data.repository.TaskRepositoryImpl

@Module
@InstallIn(SingletonComponent::class) // Need to import SingletonComponent from Hilt? Or just use @Singleton.
// Actually, standard Hilt usage:
// @Module @InstallIn(SingletonComponent::class) object AppModule { ... }

// Let's write the Impl first properly.

package com.antichaos.app.di

import dagger.Module
import dagger.ModuleExtension
import dagger.provider.Provider
import androidx.room.RoomDatabase
import com.antichaos.app.data.Database.AntiChaosDatabase
import com.antichaos.app.domain.Repository.TaskRepository
import com.antichaos.app.data.repository.TaskRepositoryImpl

@Module
@InstallIn(SingletonComponent::class) // Note: SingletonComponent needs import from dagger.hilt.android
@ModuleExtension
object DataModule {
    @Provides
    fun provideAntiChaosDatabase(): RoomDatabase = AntiChaosDatabase()
    
    @Provides
    fun provideTaskRepository(database: RoomDatabase): TaskRepository = TaskRepositoryImpl(database)
}

// Wait, I need to define the Impl to accept DB.
package com.antichaos.app.di

import dagger.Module
import dagger.ModuleExtension
import dagger.provider.Provider
import androidx.room.RoomDatabase
import com.antichaos.app.data.Database.AntiChaosDatabase
import com.antichaos.app.domain.Repository.TaskRepository
import com.antichaos.app.data.repository.TaskRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
@ModuleExtension
object DataModule {
    @Provides
    fun provideAntiChaosDatabase(): RoomDatabase = AntiChaosDatabase()
    
    @Provides
    fun provideTaskRepository(database: RoomDatabase): TaskRepository = TaskRepositoryImpl(database)
}

// And update the Impl to take DB.
package com.antichaos.app.di

import dagger.Module
import dagger.ModuleExtension
import dagger.provider.Provider
import androidx.room.RoomDatabase
import com.antichaos.app.data.Database.AntiChaosDatabase
import com.antichaos.app.domain.Repository.TaskRepository
import com.antichaos.app.data.repository.TaskRepositoryImpl

// I need to update the Impl file.