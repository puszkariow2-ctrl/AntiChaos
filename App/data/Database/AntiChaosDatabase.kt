package com.antichaos.app.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.antichaos.app.data.dao.TaskDao

abstract class AntiChaosDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    
    companion object {
        const val VERSION = 1
    }
}