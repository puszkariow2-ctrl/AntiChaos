package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.CoachingSessionEntity
import com.antichaos.app.data.local.entity.TechniqueEntity
import com.antichaos.app.data.local.entity.UserStateLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoachDao {
    // Techniques (seeded content)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechnique(technique: TechniqueEntity): Long

    @Query("SELECT * FROM techniques WHERE code = :code")
    suspend fun getTechniqueByCode(code: String): TechniqueEntity?

    @Query("SELECT * FROM techniques ORDER BY name ASC")
    fun getAllTechniques(): Flow<List<TechniqueEntity>>

    // Get techniques by category
    @Query("SELECT * FROM techniques WHERE category = :category ORDER BY name ASC")
    fun getTechniquesByCategory(category: Int): Flow<List<TechniqueEntity>>

    // Get techniques relevant to a trigger/emotion
    @Query("""
        SELECT * FROM techniques 
        WHERE triggersJson LIKE '%' || :trigger || '%'
        LIMIT 10
    """)
    suspend fun getTechniquesForTrigger(trigger: String): List<TechniqueEntity>

    // Coaching sessions history
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: CoachingSessionEntity): Long

    @Query("SELECT * FROM coaching_sessions ORDER BY createdAtEpochSeconds DESC LIMIT :limit")
    fun getRecentSessions(limit: Int = 20): Flow<List<CoachingSessionEntity>>

    // User state logs (for pattern detection)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStateLog(log: UserStateLogEntity): Long

    @Query("""
        SELECT * FROM user_state_logs 
        WHERE timestampEpochSeconds >= :fromDate
        ORDER BY timestampEpochSeconds DESC
        LIMIT :limit
    """)
    suspend fun getRecentStateLogs(fromDate: Long, limit: Int = 100): List<UserStateLogEntity>

    // Mood trend for AI insights
    @Query("""
        SELECT mood, COUNT(*) as count 
        FROM user_state_logs 
        WHERE mood IS NOT NULL AND timestampEpochSeconds >= :fromDate
        GROUP BY mood
    """)
    suspend fun getMoodTrend(fromDate: Long): List<MoodCount>

    // Trigger frequency analysis
    @Query("""
        SELECT triggerType, COUNT(*) as count 
        FROM user_state_logs 
        WHERE triggerType IS NOT NULL AND triggerType != 5 AND timestampEpochSeconds >= :fromDate
        GROUP BY triggerType
        ORDER BY count DESC
    """)
    suspend fun getTriggerFrequency(fromDate: Long): List<TriggerCount>

    data class MoodCount(val mood: Int?, val count: Int)
    data class TriggerCount(val triggerType: Int?, val count: Int)
}
