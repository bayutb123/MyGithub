package com.bayutb123.mygithub.presentation.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
}
