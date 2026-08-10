package com.antichaos.app.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toDisplayString(): String = if (isCompleted) "✅ $title" else "⏳ $title"
}