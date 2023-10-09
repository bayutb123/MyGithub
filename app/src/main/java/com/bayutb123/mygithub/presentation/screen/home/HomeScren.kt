package com.bayutb123.mygithub.presentation.screen.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bayutb123.mygithub.presentation.screen.Screen
import com.bayutb123.mygithub.presentation.screen.home.recommendation.RecommendationScreen
import com.bayutb123.mygithub.presentation.screen.home.saved.SavedScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val homeNavController = rememberNavController()
    var selectedIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = homeNavController,
                selectedIndex = selectedIndex,
                onClick = {
                    selectedIndex = it
                }
            )
        }
    ) { paddingValue ->
        NavHost(
            modifier = modifier.padding(paddingValue),
            navController = homeNavController,
            startDestination = Screen.Recommendation.route
        ) {
            composable(Screen.Saved.route) {
                SavedScreen(
                    onUserClick = {
                        navController.navigate(Screen.Detail.route.replace("{userName}", it))
                    }
                )
            }
            composable(Screen.Recommendation.route) {
                RecommendationScreen(
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    onClick: (Int) -> Unit,
    navController: NavController,
    selectedIndex: Int = 0
) {
    NavigationBar(
        modifier = Modifier.height(56.dp),
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.DataUsage, contentDescription = "For you") },
            selected = 0 == selectedIndex,
            onClick = {
                onClick(0)
                navController.navigate(Screen.Recommendation.route)
            }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Bookmarks, contentDescription = "Saved") },
            selected = 1 == selectedIndex,
            onClick = {
                onClick(1)
                navController.navigate(Screen.Saved.route)
            }
        )
    }

}
