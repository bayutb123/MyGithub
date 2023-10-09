package com.bayutb123.mygithub.presentation.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Saved : Screen("saved")
    object Recommendation : Screen("recommendation")
    object Detail : Screen("detail/{userName}")
}
