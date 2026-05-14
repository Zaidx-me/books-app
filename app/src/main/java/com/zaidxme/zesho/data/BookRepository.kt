package com.zaidxme.zesho.data

import com.zaidxme.zesho.data.local.BookDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(private val bookDao: BookDao) {
    val savedBooks: Flow<List<Book>> = bookDao.getAllSavedBooks().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun saveBook(book: Book) {
        bookDao.saveBook(book.toEntity())
    }

    suspend fun removeBook(book: Book) {
        bookDao.deleteBook(book.toEntity())
    }

    fun isBookSaved(bookId: String): Flow<Boolean> = bookDao.isBookSaved(bookId)

    suspend fun updateProgress(bookId: String, progress: Int) {
        bookDao.updateProgress(bookId, progress)
    }
}
