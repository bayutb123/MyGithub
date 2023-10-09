package com.bayutb123.mygithub.presentation.screen.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = homeNavController,
            )
        }
    ) { paddingValue ->
        NavHost(
            modifier = modifier.padding(paddingValue),
            navController = homeNavController,
            startDestination = Screen.Recommendation.route,
        ) {
            composable(Screen.Saved.route) {
                SavedScreen(
                    onItemClick = {
                        navController.navigate(Screen.Detail.route.replace("{userName}", it))
                    }
                )
            }
            composable(Screen.Recommendation.route) {
                RecommendationScreen(
                    onItemClick = {
                        navController.navigate(Screen.Detail.route.replace("{userName}", it))
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavController,
) {
    val items = listOf(
        Screen.Recommendation,
        Screen.Saved,
    )
    NavigationBar(
        modifier = Modifier.height(56.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { item.imageVector?.let { Icon(imageVector = it, contentDescription = item.route) } },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

}
