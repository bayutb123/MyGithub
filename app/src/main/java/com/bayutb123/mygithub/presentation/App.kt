package com.bayutb123.mygithub.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bayutb123.mygithub.presentation.screen.home.HomeScreen
import com.bayutb123.mygithub.presentation.screen.Screen

@Composable
fun App(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}