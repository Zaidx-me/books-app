package com.zaidxme.zesho.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM saved_books ORDER BY lastAccessed DESC")
    fun getAllSavedBooks(): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: BookEntity)

    @Delete
    suspend fun deleteBook(book: BookEntity)

    @Query("UPDATE saved_books SET progress = :progress, lastAccessed = :timestamp WHERE id = :bookId")
    suspend fun updateProgress(bookId: String, progress: Int, timestamp: Long = System.currentTimeMillis())

    @Query("SELECT EXISTS(SELECT 1 FROM saved_books WHERE id = :bookId)")
    fun isBookSaved(bookId: String): Flow<Boolean>
}
