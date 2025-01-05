package com.example.bin_test.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bin_test.feature.listScreen.ListScreen
import com.example.bin_test.feature.mainScreen.MainScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    val focusManager = LocalFocusManager.current

    NavHost(
        navController = navController,
        startDestination = "main_screen",
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }) { focusManager.clearFocus() },
    ) {
        // Экран MainScreen
        composable(
            route = "main_screen",
        ) {
            MainScreen(
                onNavigateToListScreen = { navController.navigate("list_screen") }
            )
        }

        // Экран ListScreen
        composable("list_screen") {
            ListScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}


