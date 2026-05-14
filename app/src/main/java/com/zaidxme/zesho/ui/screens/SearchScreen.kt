package com.zaidxme.zesho.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zaidxme.zesho.data.BookCollections
import com.zaidxme.zesho.ui.components.BookBottomSheet
import com.zaidxme.zesho.ui.components.BookCard
import com.zaidxme.zesho.ui.components.ModernSearchBar
import com.zaidxme.zesho.ui.components.GlassTopBar
import com.zaidxme.zesho.ui.components.SkeletonBookCard
import com.zaidxme.zesho.ui.theme.DarkSlate
import com.zaidxme.zesho.ui.theme.DarkerGray
import com.zaidxme.zesho.ui.theme.PrimaryPurple
import com.zaidxme.zesho.ui.theme.White
import com.zaidxme.zesho.ui.viewmodel.BookViewModel
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialCategory: String? = null,
    viewModel: BookViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(initialCategory ?: "All") }
    
    var selectedBookForSheet by remember { mutableStateOf<com.zaidxme.zesho.data.Book?>(null) }
    
    val apiResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val categories = listOf("All", "PUCIT-FCIT", "Programming", "Novels", "Textbooks")

    val localBooks = remember(selectedCategory) {
        when (selectedCategory) {
            "PUCIT-FCIT" -> BookCollections.PUCIT_FCIT_BOOKS
            "Programming" -> BookCollections.PROGRAMMING_BOOKS
            "Novels" -> BookCollections.COMPUTER_SCIENCE_NOVELS
            "Textbooks" -> BookCollections.ACADEMIC_TEXTBOOKS
            else -> {
                BookCollections.PUCIT_FCIT_BOOKS +
                        BookCollections.PROGRAMMING_BOOKS +
                        BookCollections.COMPUTER_SCIENCE_NOVELS +
                        BookCollections.ACADEMIC_TEXTBOOKS
            }
        }
    }

    val filteredLocalBooks = remember(searchQuery, localBooks) {
        if (searchQuery.isBlank()) localBooks
        else localBooks.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.authors.any { author -> author.contains(searchQuery, ignoreCase = true) }
        }
    }

    // Debounce search for API
    LaunchedEffect(searchQuery, selectedCategory) {
        val query = if (searchQuery.isBlank() && selectedCategory != "All") {
            selectedCategory
        } else {
            searchQuery
        }

        if (query.length > 2) {
            delay(500)
            viewModel.searchBooks(query)
        } else if (query.isEmpty()) {
            viewModel.searchBooks("")
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkSlate)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(88.dp)) // Padding for GlassTopBar

            Text(
                text = "Discover",
                style = MaterialTheme.typography.displayMedium,
                color = White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernSearchBar(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Books, authors, or topics..."
            )

            Spacer(modifier = Modifier.height(16.dp))

            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = DarkerGray.copy(alpha = 0.3f),
                            labelColor = White.copy(alpha = 0.6f),
                            selectedContainerColor = PrimaryPurple,
                            selectedLabelColor = White
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = White.copy(alpha = 0.1f),
                            selectedBorderColor = PrimaryPurple,
                            enabled = true,
                            selected = selectedCategory == category
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(5) {
                        SkeletonBookCard()
                    }
                }
            } else if (searchQuery.isNotBlank() && filteredLocalBooks.isEmpty() && apiResults.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No results found for \"$searchQuery\"",
                            color = White.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Try a different keyword",
                            color = White.copy(alpha = 0.4f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            } else if (searchQuery.isBlank() && selectedCategory == "All") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = White.copy(alpha = 0.1f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Search for university resources or e-books",
                            color = White.copy(alpha = 0.4f),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (filteredLocalBooks.isNotEmpty()) {
                        item {
                            Text(
                                text = if (selectedCategory == "All") "University Resources" else selectedCategory,
                                style = MaterialTheme.typography.titleLarge,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        items(filteredLocalBooks) { book ->
                            val isSaved by viewModel.isBookSaved(book.id).collectAsState(initial = false)
                            BookCard(
                                book = book,
                                isSaved = isSaved,
                                onSaveToggle = { viewModel.toggleSaveBook(book) },
                                onClick = { selectedBookForSheet = book }
                            )
                        }
                    }

                    if (apiResults.isNotEmpty()) {
                        item {
                            Text(
                                text = "Global Library",
                                style = MaterialTheme.typography.titleLarge,
                                color = White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                            )
                        }
                        items(apiResults) { book ->
                            val isSaved by viewModel.isBookSaved(book.id).collectAsState(initial = false)
                            BookCard(
                                book = book,
                                isSaved = isSaved,
                                onSaveToggle = { viewModel.toggleSaveBook(book) },
                                onClick = { selectedBookForSheet = book }
                            )
                        }
                    }
                }
            }
        }

        GlassTopBar(
            title = "Search",
            alpha = 0.8f // Keep it slightly translucent always for SearchScreen
        )

        selectedBookForSheet?.let { book ->
            val isSaved by viewModel.isBookSaved(book.id).collectAsState(initial = false)
            BookBottomSheet(
                book = book,
                isSaved = isSaved,
                onSaveToggle = { viewModel.toggleSaveBook(book) },
                onDismiss = { selectedBookForSheet = null }
            )
        }
    }
}
