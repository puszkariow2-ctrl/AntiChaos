package com.antichaos.app.database

import androidx.room.*

// ─── Practice Entity ──────────────────────────────────────

@Entity(tableName = "practices")
data class Practice(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,              // "Box Breathing", "4-7-8 Дихання"
    val category: Int,              // 0=breathing, 1=grounding, 2=physical, 3=mental, 4=emotional, 5=spiritual
    val durationSeconds: Long,      // practice duration in seconds (e.g., 120 for 2 min)
    val difficulty: Int,            // 0=easy, 1=medium, 2=hard
    val instructions: String,       // step-by-step JSON or markdown
    val whenToUse: String,          // context tags: "anxiety", "focus", "sleep", "stress"
    val isFavorite: Boolean = false
)

// ─── Practice Session Entity ──────────────────────────────

@Entity(tableName = "practice_sessions")
data class PracticeSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val practiceId: Long,           // FK to practices.id
    val completedAt: Long,          // epoch timestamp
    val moodBefore: Int? = null,    // user state before practice
    val moodAfter: Int? = null,     // user state after practice (optional feedback)
    val notes: String? = null       // optional user notes about the session
)

// ─── Enums / Helpers ──────────────────────────────────────

enum class PracticeCategory(val value: Int, val label: String) {
    BREATHING(0, "Дихальні"),
    GROUNDING(1, "Заземлення"),
    PHYSICAL(2, "Тілесні"),
    MENTAL(3, "Ментальні"),
    EMOTIONAL(4, "Емоційні"),
    SPIRITUAL(5, "Духовні/Сенсові")
}

enum class PracticeDifficulty(val value: Int, val label: String) {
    EASY(0, "Легка"),
    MEDIUM(1, "Середня"),
    HARD(2, "Важка")
}

// ─── DAO ──────────────────────────────────────────────────

@Dao
interface PracticeDao {
    @Query("SELECT COUNT(*) FROM practices")
    suspend fun count(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(practices: List<Practice>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(practice: Practice)

    @Query("SELECT * FROM practices ORDER BY title")
    fun getAllPractices(): kotlinx.coroutines.flow.Flow<List<Practice>>

    @Query("SELECT * FROM practices WHERE category = :category ORDER BY title")
    fun getPracticesByCategory(category: Int): kotlinx.coroutines.flow.Flow<List<Practice>>

    @Query("SELECT * FROM practices WHERE id = :id")
    suspend fun getPracticeById(id: Long): Practice?

    @Query("SELECT * FROM practices WHERE whenToUse LIKE '%' || :tag || '%' LIMIT 5")
    fun getPracticesForTag(tag: String): kotlinx.coroutines.flow.Flow<List<Practice>>

    @Query("SELECT * FROM practices WHERE isFavorite = 1 ORDER BY title")
    fun getFavoritePractices(): kotlinx.coroutines.flow.Flow<List<Practice>>

    @Update
    suspend fun update(practice: Practice)

    // Sessions
    @Insert
    suspend fun insertSession(session: PracticeSession)

    @Query("SELECT * FROM practice_sessions WHERE practiceId = :practiceId ORDER BY completedAt DESC")
    fun getSessionHistory(practiceId: Long): kotlinx.coroutines.flow.Flow<List<PracticeSession>>

    @Query("SELECT COUNT(*) FROM practice_sessions WHERE practiceId = :practiceId")
    suspend fun getTotalSessions(practiceId: Long): Int
}
