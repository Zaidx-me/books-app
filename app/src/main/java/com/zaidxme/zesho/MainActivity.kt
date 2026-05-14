package com.zaidxme.zesho

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zaidxme.zesho.ui.screens.LoginScreen
import com.zaidxme.zesho.ui.screens.SignupScreen
import com.zaidxme.zesho.ui.screens.ZeshoLandingScreen
import com.zaidxme.zesho.ui.screens.SearchScreen
import com.zaidxme.zesho.ui.screens.SavedScreen
import com.zaidxme.zesho.ui.screens.BookDetailScreen
import com.zaidxme.zesho.ui.theme.ZeshoTheme

import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zaidxme.zesho.ui.viewmodel.BookViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZeshoTheme {
                ZeshoApp()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("landing", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Bookmarks : Screen("bookmarks", "Saved", Icons.Default.Bookmark)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@Composable
fun ZeshoApp() {
    val localContext = LocalContext.current
    val navController = rememberNavController()
    val viewModel: BookViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.route in listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Bookmarks.route,
        Screen.Profile.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    val items = listOf(
                        Screen.Home,
                        Screen.Search,
                        Screen.Bookmarks,
                        Screen.Profile
                    )
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate("signup")
                    }
                )
            }
            
            composable("signup") {
                SignupScreen(
                    onSignupSuccess = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            
            composable(Screen.Home.route) {
                ZeshoLandingScreen(
                    onExploreClick = { 
                        navController.navigate(Screen.Search.route) 
                    },
                    onCategoryClick = { category ->
                        navController.navigate(Screen.Search.route + "?category=$category")
                    },
                    onBookClick = { id ->
                        navController.navigate("book_detail/$id")
                    },
                    viewModel = viewModel
                )
            }

            composable(
                route = Screen.Search.route + "?category={category}",
                arguments = listOf(navArgument("category") { 
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")
                SearchScreen(
                    initialCategory = category,
                    onBookClick = { id ->
                        navController.navigate("book_detail/$id")
                    },
                    viewModel = viewModel
                )
            }

            composable(
                route = "book_detail/{bookId}",
                arguments = listOf(navArgument("bookId") { type = NavType.StringType })
            ) { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
                BookDetailScreen(
                    bookId = bookId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onReadClick = { book ->
                        if (book.downloadUrl.isNotEmpty()) {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.downloadUrl))
                                localContext.startActivity(intent)
                            } catch (e: Exception) {
                                // Handle error
                            }
                        }
                    }
                )
            }

            composable(Screen.Bookmarks.route) {
                SavedScreen(
                    onBookClick = { id ->
                        navController.navigate("book_detail/$id")
                    },
                    viewModel = viewModel
                )
            }

            composable(Screen.Profile.route) {
                // Placeholder for Profile
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("Your profile settings.")
                }
            }
        }
    }
}
