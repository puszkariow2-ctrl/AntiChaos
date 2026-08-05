package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.EveningReviewEntity
import com.antichaos.app.data.local.entity.JournalEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    // Journal entries CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntryEntity): Long

    @Update
    suspend fun updateEntry(entry: JournalEntryEntity)

    @Delete
    suspend fun deleteEntry(entry: JournalEntryEntity)

    @Query("SELECT * FROM journal_entries WHERE id = :entryId")
    suspend fun getEntryById(entryId: Long): JournalEntryEntity?

    // Get entries for today
    @Query("SELECT * FROM journal_entries WHERE dateEpochDays = :today ORDER BY createdAtEpochSeconds DESC")
    fun getEntriesForToday(today: Long): Flow<List<JournalEntryEntity>>

    // Recent entries (for history view)
    @Query("SELECT * FROM journal_entries ORDER BY createdAtEpochSeconds DESC LIMIT :limit")
    suspend fun getRecentEntries(limit: Int = 50): List<JournalEntryEntity>

    // Entries by type
    @Query("SELECT * FROM journal_entries WHERE entryType = :type ORDER BY createdAtEpochSeconds DESC LIMIT :limit")
    suspend fun getEntriesByType(type: Int, limit: Int = 20): List<JournalEntryEntity>

    // Search in content
    @Query("SELECT * FROM journal_entries WHERE content LIKE '%' || :query || '%' ORDER BY createdAtEpochSeconds DESC LIMIT 30")
    suspend fun searchEntries(query: String): List<JournalEntryEntity>

    // Evening reviews
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: EveningReviewEntity): Long

    @Query("SELECT * FROM evening_reviews WHERE dateEpochDays = :date")
    suspend fun getReviewForDate(date: Long): EveningReviewEntity?

    @Query("SELECT * FROM evening_reviews ORDER BY completedAtEpochSeconds DESC LIMIT :limit")
    suspend fun getRecentReviews(limit: Int = 30): List<EveningReviewEntity>

    // Mood stats for visualization
    @Query("""
        SELECT mood, COUNT(*) as count 
        FROM journal_entries 
        WHERE mood IS NOT NULL AND createdAtEpochSeconds >= :fromDate
        GROUP BY mood
    """)
    suspend fun getMoodDistribution(fromDate: Long): List<MoodCount>

    // Day ratings trend
    @Query("""
        SELECT dateEpochDays, dayRating FROM evening_reviews 
        WHERE dayRating IS NOT NULL AND completedAtEpochSeconds >= :fromDate
        ORDER BY dateEpochDays ASC
    """)
    suspend fun getDayRatingsTrend(fromDate: Long): List<DayRating>

    data class MoodCount(val mood: Int?, val count: Int)
    data class DayRating(val dateEpochDays: Long, val dayRating: Int?)
}
