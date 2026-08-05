package com.antichaos.app.data.local.dao

import androidx.room.*
import com.antichaos.app.data.local.entity.BookEntity
import com.antichaos.app.data.local.entity.MethodologyEntity
import com.antichaos.app.data.local.entity.QuoteEntity
import com.antichaos.app.data.local.entity.SavedNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {
    // Books
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity): Long

    @Update
    suspend fun updateBook(book: BookEntity)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Long): BookEntity?

    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE category = :category ORDER BY title ASC")
    fun getBooksByCategory(category: String): Flow<List<BookEntity>>

    // Quotes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity): Long

    @Update
    suspend fun updateQuote(quote: QuoteEntity)

    @Query("SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomFavoriteQuote(): QuoteEntity?

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuote(): QuoteEntity?

    @Query("SELECT * FROM quotes WHERE categoriesJson LIKE '%' || :category || '%' LIMIT 20")
    suspend fun getQuotesByCategory(category: String): List<QuoteEntity>

    // Methodologies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMethodology(methodology: MethodologyEntity): Long

    @Query("SELECT * FROM methodologies ORDER BY name ASC")
    fun getAllMethodologies(): Flow<List<MethodologyEntity>>

    @Query("SELECT * FROM methodologies WHERE id = :methodId")
    suspend fun getMethodologyById(methodId: Long): MethodologyEntity?

    // Saved notes (user's own)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: SavedNoteEntity): Long

    @Update
    suspend fun updateNote(note: SavedNoteEntity)

    @Delete
    suspend fun deleteNote(note: SavedNoteEntity)

    @Query("SELECT * FROM saved_notes ORDER BY updatedAtEpochSeconds DESC")
    fun getAllNotes(): Flow<List<SavedNoteEntity>>

    // Search across all library content
    @Query("""
        SELECT 'book' as type, id, title, author as subtitle 
        FROM books WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'
        UNION ALL
        SELECT 'quote' as type, id, text as title, author as subtitle 
        FROM quotes WHERE text LIKE '%' || :query || '%'
        UNION ALL
        SELECT 'note' as type, id, title, '' as subtitle 
        FROM saved_notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'
        LIMIT 30
    """)
    suspend fun searchLibrary(query: String): List<SearchResult>

    data class SearchResult(
        val type: String,   // "book", "quote", "note"
        val id: Long,
        val title: String,
        val subtitle: String
    )
}
