package com.bayutb123.mygithub.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bayutb123.mygithub.presentation.screen.Screen
import com.bayutb123.mygithub.presentation.screen.detail.DetailScreen
import com.bayutb123.mygithub.presentation.screen.home.HomeScreen

@Composable
fun App(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Detail.route, arguments = listOf(navArgument("userName") {
            type = NavType.StringType
        })) {backStackEntry ->
            DetailScreen(
                userName = backStackEntry.arguments?.getString("userName") ?: ""
            )
        }
    }
}