package com.bayutb123.mygithub.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.mygithub.domain.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val users = homeViewModel.users

    Scaffold { paddingValue->
        Column(
            modifier
                .padding(paddingValue)
                .fillMaxWidth()
        ) {
            Box(modifier.padding(8.dp)) {
                SearchBar(
                    modifier = modifier.fillMaxWidth(),
                    onSearch = {

                    }
                )
            }

            LazyColumn {

                items(users) { user ->
                    UserItem(user = user )
                }

            }
        }
    }
}

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
) {

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(text = user.login)
    }

}