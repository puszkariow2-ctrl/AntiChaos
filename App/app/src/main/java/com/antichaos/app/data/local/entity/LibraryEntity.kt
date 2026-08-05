package com.antichaos.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val author: String,
    val category: String,               // "productivity", "psychology", "habits" etc.
    val description: String,            // 2-3 sentences about the book
    val keyIdeasJson: String,           // JSON array of 5-10 key ideas
    val practicalExercisesJson: String,// JSON array of exercises from the book
    val isReadByUser: Boolean = false,
    val userNotes: String?,             // user's personal notes on this book
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val author: String,
    val source: String?,                // book/speech/source
    val categoriesJson: String,         // JSON array: ["motivation", "discipline"]
    val isFavorite: Boolean = false,
    val appliedToTaskId: Long?          // which task/habit this quote inspired
)

@Entity(tableName = "methodologies")
data class MethodologyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,                   // e.g., "Pomodoro Technique"
    val description: String,            // what it is and why use it
    val stepsJson: String,              // JSON array of how-to steps
    val whenToUseJson: String,          // JSON array of triggers/use cases
    val sourceBookId: Long?,            // which book this comes from
    val relatedTechniquesJson: String   // JSON array of technique codes
)

@Entity(tableName = "saved_notes")
data class SavedNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val tagsJson: String,               // JSON array of tags
    val sourceType: Int?,               // NoteSourceType enum or null for own thought
    val createdAtEpochSeconds: Long = System.currentTimeMillis() / 1000,
    val updatedAtEpochSeconds: Long = System.currentTimeMillis() / 1000
)

enum class NoteSourceType(val value: Int) {
    BOOK(0), QUOTE(1), OWN_THOUGHT(2), AI_INSIGHT(3), EXTERNAL_LINK(4);

    companion object {
        fun fromValue(value: Int?) = value?.let { entries.find { e -> e.value == it } }
    }
}
