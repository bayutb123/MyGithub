package com.bayutb123.mygithub.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val imageVector: ImageVector? = null) {
    object Home : Screen("home")
    object Saved : Screen("saved", Icons.Default.Bookmarks)
    object Recommendation : Screen("recommendation", Icons.Default.DataUsage)
    object Detail : Screen("detail/{userName}")
}
