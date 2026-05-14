package com.zaidxme.zesho.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zaidxme.zesho.data.BookCollections
import com.zaidxme.zesho.ui.components.*
import com.zaidxme.zesho.ui.components.SkeletonBookCard
import com.zaidxme.zesho.ui.components.GlassTopBar
import com.zaidxme.zesho.ui.theme.*
import com.zaidxme.zesho.ui.viewmodel.BookViewModel
import kotlinx.coroutines.delay

@Composable
fun ZeshoLandingScreen(
    modifier: Modifier = Modifier,
    onExploreClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
    onBookClick: (String) -> Unit = {},
    viewModel: BookViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    
    val apiResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    var selectedSemester by remember { mutableStateOf("Semester 1") }
    var selectedBookForSheet by remember { mutableStateOf<com.zaidxme.zesho.data.Book?>(null) }

    val localBooks = remember {
        BookCollections.PUCIT_FCIT_BOOKS +
                BookCollections.PROGRAMMING_BOOKS +
                BookCollections.COMPUTER_SCIENCE_NOVELS +
                BookCollections.ACADEMIC_TEXTBOOKS
    }

    // Debounce search
    LaunchedEffect(searchQuery) {
        if (searchQuery.length > 2) {
            delay(500)
            viewModel.searchBooks(searchQuery)
        }
    }

    val filteredLocalBooks = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else localBooks.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
                    it.authors.any { author -> author.contains(searchQuery, ignoreCase = true) }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkSlate)
    ) {
        // Decorative background elements
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-150).dp, y = (-150).dp)
                .background(PrimaryPurple.copy(alpha = 0.1f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            HeroSection(onExploreClick)

            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {
                ModernSearchBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = "Search for university books, notes..."
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (searchQuery.isBlank()) {
                    Text(
                        text = "Your Dashboard",
                        style = MaterialTheme.typography.headlineMedium,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Tailored for your current semester",
                        style = MaterialTheme.typography.bodyMedium,
                        color = White.copy(alpha = 0.6f)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SemesterSelector(
                        selectedSemester = selectedSemester,
                        onSemesterSelected = { selectedSemester = it }
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Core Resources",
                        style = MaterialTheme.typography.titleLarge,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val semesterBooks = BookCollections.SEMESTER_MAP[selectedSemester] ?: emptyList()
                    androidx.compose.foundation.lazy.LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(end = 24.dp)
                    ) {
                        items(semesterBooks) { book ->
                            val isSaved by viewModel.isBookSaved(book.id).collectAsState(initial = false)
                            BookCardSmall(
                                book = book,
                                onClick = { selectedBookForSheet = book }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Explore Categories",
                        style = MaterialTheme.typography.titleLarge,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CategoryChips(onCategoryClick)

                    Spacer(modifier = Modifier.height(32.dp))
                    
                    FeaturesSection()
                    
                    Spacer(modifier = Modifier.height(100.dp)) // Bottom Nav padding
                }
            }
        }

        GlassTopBar(
            title = "ZESHO",
            alpha = if (scrollState.value > 100) 0.8f else 0f
        )

        if (searchQuery.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 400.dp)
                    .background(DarkSlate)
            ) {
                if (isLoading) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(3) {
                            SkeletonBookCard()
                        }
                    }
                } else if (filteredLocalBooks.isEmpty() && apiResults.isEmpty()) {
                    Text(
                        text = "No results found for \"$searchQuery\"",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center,
                        color = White.copy(alpha = 0.6f)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (filteredLocalBooks.isNotEmpty()) {
                            item {
                                Text(
                                    text = "University Resources",
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
                                    text = "Global Library (E-books)",
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

                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }
        }

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

@Composable
fun HeroSection(onExploreClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .background(PrimaryGradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "ZESHO",
                fontSize = 48.sp,
                fontWeight = FontWeight.ExtraBold,
                color = White,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Modern Educational Resource Sharing",
                fontSize = 18.sp,
                color = White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            ZeshoPrimaryButton(
                text = "Explore Resources",
                onClick = onExploreClick
            )
        }
    }
}

private data class Category(val title: String, val icon: ImageVector)

@Composable
private fun CategoryChips(onCategoryClick: (String) -> Unit) {
    val categories = listOf(
        Category("PUCIT-FCIT", Icons.Default.School),
        Category("Programming", Icons.Default.Code),
        Category("Novels", Icons.Default.MenuBook),
        Category("Textbooks", Icons.Default.LibraryBooks),
        Category("Mathematics", Icons.Default.Functions),
        Category("Science", Icons.Default.Science)
    )

    androidx.compose.foundation.lazy.LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 24.dp)
    ) {
        items(categories) { category ->
            InputChip(
                selected = false,
                onClick = { onCategoryClick(category.title) },
                label = { 
                    Text(
                        category.title, 
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontWeight = FontWeight.Medium 
                    ) 
                },
                leadingIcon = {
                    Icon(
                        category.icon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                },
                colors = InputChipDefaults.inputChipColors(
                    containerColor = DarkerGray.copy(alpha = 0.5f),
                    labelColor = White,
                    leadingIconColor = PrimaryPurple
                ),
                border = InputChipDefaults.inputChipBorder(
                    enabled = true,
                    selected = false,
                    borderColor = White.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
private fun FeaturesSection() {
    Column {
        Text(
            text = "Why ZESHO?",
            style = MaterialTheme.typography.displayMedium,
            color = White
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        FeatureItem(
            title = "Academic Excellence",
            description = "High quality resources curated for your academic success."
        )
        Spacer(modifier = Modifier.height(16.dp))
        FeatureItem(
            title = "Collaborative Learning",
            description = "Share and grow with a community of dedicated learners."
        )
        Spacer(modifier = Modifier.height(16.dp))
        FeatureItem(
            title = "Instant Access",
            description = "Find what you need, when you need it, anywhere."
        )
    }
}

@Composable
fun SemesterSelector(
    selectedSemester: String,
    onSemesterSelected: (String) -> Unit
) {
    val semesters = listOf("Semester 1", "Semester 2", "Semester 3")
    
    androidx.compose.foundation.lazy.LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(semesters) { semester ->
            val isSelected = selectedSemester == semester
            Surface(
                onClick = { onSemesterSelected(semester) },
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) PrimaryPurple else DarkerGray.copy(alpha = 0.5f),
                border = if (isSelected) null else AssistChipDefaults.assistChipBorder(enabled = true, borderColor = White.copy(alpha = 0.1f)),
                modifier = Modifier.height(48.dp)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = semester,
                        color = if (isSelected) White else White.copy(alpha = 0.6f),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun BookCardSmall(
    book: com.zaidxme.zesho.data.Book,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(160.dp)
            .height(220.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkerGray.copy(alpha = 0.3f)),
        border = AssistChipDefaults.assistChipBorder(enabled = true, borderColor = White.copy(alpha = 0.05f))
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(PrimaryPurple.copy(alpha = 0.2f))
            ) {
                if (book.thumbnail.isNotEmpty()) {
                    // AsyncImage here in real implementation
                } else {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp).align(Alignment.Center),
                        tint = White.copy(alpha = 0.1f)
                    )
                }
            }
            
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Text(
                    text = book.authors.firstOrNull() ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = White.copy(alpha = 0.6f),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}
