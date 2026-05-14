package com.zaidxme.zesho.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zaidxme.zesho.ui.components.BookCard
import com.zaidxme.zesho.ui.theme.DarkSlate
import com.zaidxme.zesho.ui.theme.White
import com.zaidxme.zesho.ui.viewmodel.BookViewModel

@Composable
fun SavedScreen(
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = viewModel()
) {
    val savedBooks by viewModel.savedBooks.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkSlate)
            .padding(24.dp)
    ) {
        Text(
            text = "My Shelf",
            style = MaterialTheme.typography.displayMedium,
            color = White,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "${savedBooks.size} items in your collection",
            style = MaterialTheme.typography.bodyLarge,
            color = White.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (savedBooks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your shelf is empty.\nStart saving books to build your library!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = White.copy(alpha = 0.4f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(savedBooks) { book ->
                    BookCard(
                        book = book,
                        isSaved = true,
                        onSaveToggle = { viewModel.toggleSaveBook(book) },
                        onClick = { onBookClick(book.id) }
                    )
                }
            }
        }
    }
}
