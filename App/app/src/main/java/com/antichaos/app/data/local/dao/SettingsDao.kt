package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.DailyAnchorEntity
import com.antichaos.app.data.local.entity.LifeAreaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    // Daily anchors (user's schedule)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnchor(anchor: DailyAnchorEntity): Long

    @Update
    suspend fun updateAnchor(anchor: DailyAnchorEntity)

    @Delete
    suspend fun deleteAnchor(anchor: DailyAnchorEntity)

    @Query("SELECT * FROM daily_anchors WHERE isActive = 1 ORDER BY timeOfDayMinutes ASC")
    fun getActiveAnchors(): Flow<List<DailyAnchorEntity>>

    // Get next anchor from now (for home screen "what's next")
    @Query("""
        SELECT * FROM daily_anchors 
        WHERE isActive = 1 AND timeOfDayMinutes > :currentMinutes
        ORDER BY timeOfDayMinutes ASC LIMIT 1
    """)
    suspend fun getNextAnchor(currentMinutes: Int): DailyAnchorEntity?

    // Life areas
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLifeArea(area: LifeAreaEntity): Long

    @Update
    suspend fun updateLifeArea(area: LifeAreaEntity)

    @Query("SELECT * FROM life_areas WHERE isActive = 1 ORDER BY orderIndex ASC")
    fun getActiveLifeAreas(): Flow<List<LifeAreaEntity>>

    @Query("UPDATE life_areas SET attentionLevel = :level WHERE id = :areaId")
    suspend fun updateAttentionLevel(areaId: Long, level: Int)
}
