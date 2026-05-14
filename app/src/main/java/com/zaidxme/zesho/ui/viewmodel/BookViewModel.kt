package com.zaidxme.zesho.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaidxme.zesho.api.RetrofitInstance
import com.zaidxme.zesho.data.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.zaidxme.zesho.data.BookRepository
import com.zaidxme.zesho.data.local.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BookRepository
    
    init {
        val database = AppDatabase.getDatabase(application)
        repository = BookRepository(database.bookDao())
    }

    val savedBooks: StateFlow<List<Book>> = repository.savedBooks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchResults = MutableStateFlow<List<Book>>(emptyList())
    val searchResults: StateFlow<List<Book>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun toggleSaveBook(book: Book) {
        viewModelScope.launch {
            val alreadySaved = repository.isBookSaved(book.id).stateIn(viewModelScope).value
            if (alreadySaved) {
                repository.removeBook(book)
            } else {
                repository.saveBook(book)
            }
        }
    }

    fun isBookSaved(bookId: String): Flow<Boolean> = repository.isBookSaved(bookId)

    fun searchBooks(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch from Google Books
                val googleResponse = RetrofitInstance.api.searchBooks(query)
                val googleBooks = googleResponse.items?.map { item ->
                    val categories = item.volumeInfo.categories ?: listOf("E-book")
                    Book(
                        id = item.id,
                        title = item.volumeInfo.title,
                        authors = item.volumeInfo.authors ?: listOf("Unknown Author"),
                        description = item.volumeInfo.description ?: "",
                        thumbnail = item.volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://") ?: "",
                        downloadUrl = item.accessInfo?.webReaderLink ?: item.volumeInfo.previewLink ?: "",
                        category = categories.first()
                    )
                } ?: emptyList()

                // Fetch from Open Library as well
                val olBooks = try {
                    val olResponse = RetrofitInstance.openLibraryApi.searchBooks(query)
                    olResponse.docs.map { doc ->
                        Book(
                            id = doc.key.removePrefix("/works/"),
                            title = doc.title,
                            authors = doc.author_name ?: listOf("Unknown Author"),
                            description = "Published in ${doc.first_publish_year ?: "unknown year"}",
                            thumbnail = if (doc.cover_i != null) "https://covers.openlibrary.org/b/id/${doc.cover_i}-M.jpg" else "",
                            downloadUrl = "https://openlibrary.org${doc.key}",
                            category = doc.subject?.firstOrNull() ?: "Open Library"
                        )
                    }
                } catch (e: Exception) {
                    emptyList()
                }

                // Combine results
                _searchResults.value = (googleBooks + olBooks).distinctBy { it.id }
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBookById(id: String): Book? {
        val localBooks = com.zaidxme.zesho.data.BookCollections.PUCIT_FCIT_BOOKS +
                com.zaidxme.zesho.data.BookCollections.PROGRAMMING_BOOKS +
                com.zaidxme.zesho.data.BookCollections.COMPUTER_SCIENCE_NOVELS +
                com.zaidxme.zesho.data.BookCollections.ACADEMIC_TEXTBOOKS
        
        return savedBooks.value.find { it.id == id } 
            ?: searchResults.value.find { it.id == id }
            ?: localBooks.find { it.id == id }
    }
}
