package com.bayutb123.mygithub.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bayutb123.mygithub.data.source.state.UserDetailState
import com.bayutb123.mygithub.presentation.screen.home.components.LoadingAnimation

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    userName: String
) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    detailViewModel.getUserDetail(userName)
    Scaffold { paddingValues ->
        Column(
            modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UserInfo(user = detailViewModel.state.collectAsState().value)
        }
    }
}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    user: UserDetailState
) {
    when (user) {
        is UserDetailState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }

        is UserDetailState.Success -> {
            Box(modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        modifier = modifier.size(128.dp).clip(CircleShape),
                        model = user.data.avatarUrl,
                        contentDescription = user.data.name
                    )
                    Column(modifier = modifier) {
                        Text(text = user.data.name, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.headlineSmall)
                        Text(text = user.data.login, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyMedium)
                        user.data.location?.let { Text(text = it, color = MaterialTheme.colorScheme.onPrimary) }
                    }
                }
            }
        }

        is UserDetailState.Error -> {
            Text(text = user.errorMessage)
        }
    }
}