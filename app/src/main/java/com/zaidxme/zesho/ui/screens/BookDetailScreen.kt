package com.zaidxme.zesho.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zaidxme.zesho.data.Book
import com.zaidxme.zesho.ui.components.ZeshoPrimaryButton
import com.zaidxme.zesho.ui.theme.*
import com.zaidxme.zesho.ui.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: String,
    viewModel: BookViewModel,
    onBackClick: () -> Unit,
    onReadClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    // Find the book from search results or saved books
    val savedBooks by viewModel.savedBooks.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    
    val book = remember(bookId, savedBooks, searchResults) {
        viewModel.getBookById(bookId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkSlate,
                    titleContentColor = White,
                    navigationIconContentColor = White
                )
            )
        },
        containerColor = DarkSlate
    ) { innerPadding ->
        if (book == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = PrimaryPurple)
            }
        } else {
            val isSaved by viewModel.isBookSaved(book.id).collectAsState(initial = false)
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Book Cover
                Surface(
                    modifier = Modifier
                        .size(width = 160.dp, height = 240.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    color = DarkerGray,
                    tonalElevation = 8.dp
                ) {
                    if (book.thumbnail.isNotEmpty()) {
                        AsyncImage(
                            model = book.thumbnail,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = White.copy(alpha = 0.2f),
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = book.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "by ${book.authors.joinToString(", ")}",
                    style = MaterialTheme.typography.titleMedium,
                    color = White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SuggestionChip(
                        onClick = { },
                        label = { Text(book.category) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            labelColor = PrimaryPurple,
                            containerColor = PrimaryPurple.copy(alpha = 0.1f)
                        ),
                        border = null
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ZeshoPrimaryButton(
                        text = if (isSaved) "Saved" else "Save Book",
                        onClick = { viewModel.toggleSaveBook(book) },
                        modifier = Modifier.weight(1f),
                        containerColor = if (isSaved) DarkerGray else PrimaryPurple,
                        icon = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder
                    )

                    ZeshoPrimaryButton(
                        text = "Read Now",
                        onClick = { onReadClick(book) },
                        modifier = Modifier.weight(1f),
                        icon = Icons.AutoMirrored.Filled.MenuBook
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleLarge,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = book.description.ifEmpty { "No description available for this book." },
                    style = MaterialTheme.typography.bodyLarge,
                    color = White.copy(alpha = 0.8f),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
