package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.PracticeEntity
import com.antichaos.app.data.local.entity.PracticeSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeDao {
    @Query("SELECT COUNT(*) FROM practices")
    suspend fun count(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(practices: List<PracticeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(practice: PracticeEntity)

    @Query("SELECT * FROM practices ORDER BY title")
    fun getAllPractices(): Flow<List<PracticeEntity>>

    @Query("SELECT * FROM practices WHERE category = :category ORDER BY title")
    fun getPracticesByCategory(category: Int): Flow<List<PracticeEntity>>

    @Query("SELECT * FROM practices WHERE id = :id")
    suspend fun getPracticeById(id: Long): PracticeEntity?

    @Query("SELECT * FROM practices WHERE whenToUse LIKE '%' || :tag || '%' LIMIT 5")
    fun getPracticesForTag(tag: String): Flow<List<PracticeEntity>>

    @Query("SELECT * FROM practices WHERE isFavorite = 1 ORDER BY title")
    fun getFavoritePractices(): Flow<List<PracticeEntity>>

    @Update
    suspend fun update(practice: PracticeEntity)

    // Sessions
    @Insert
    suspend fun insertSession(session: PracticeSessionEntity)

    @Query("SELECT * FROM practice_sessions WHERE practiceId = :practiceId ORDER BY completedAtEpochSeconds DESC")
    fun getSessionHistory(practiceId: Long): Flow<List<PracticeSessionEntity>>

    @Query("SELECT COUNT(*) FROM practice_sessions WHERE practiceId = :practiceId")
    suspend fun getTotalSessions(practiceId: Long): Int
}
