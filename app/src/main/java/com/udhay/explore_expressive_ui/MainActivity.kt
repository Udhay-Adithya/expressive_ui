package com.udhay.explore_expressive_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.udhay.explore_expressive_ui.ui.screens.UserDetailScreen
import com.udhay.explore_expressive_ui.ui.screens.UsersListScreen
import com.udhay.explore_expressive_ui.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            AppTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "users_list"
                ) {
                    composable("users_list") {
                        UsersListScreen(
                            onUserClick = { userId ->
                                navController.navigate("user_detail/$userId")
                            }
                        )
                    }
                    composable("user_detail/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        UserDetailScreen(
                            userId = userId,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
