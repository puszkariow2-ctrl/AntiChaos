package com.antichaos.app.data.database

import androidx.room.RoomDatabase
import com.antichaos.app.data.dao.TaskDao
import com.antichaos.app.database.Practice
import com.antichaos.app.database.PracticeDao
import com.antichaos.app.database.PracticeSession
import com.antichaos.app.database.Task

abstract class AntiChaosDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun practiceDao(): PracticeDao

    companion object {
        const val VERSION = 1
    }
}
