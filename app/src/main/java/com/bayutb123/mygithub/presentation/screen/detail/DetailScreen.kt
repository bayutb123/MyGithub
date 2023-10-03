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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bayutb123.mygithub.data.source.state.UserDetailState
import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.presentation.screen.home.components.LoadingAnimation

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    userName: String
) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    val clipboardManager = LocalClipboardManager.current
    detailViewModel.getUserDetail(userName)
    var githubUrl = ""
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                clipboardManager.setText(AnnotatedString(githubUrl))
            }) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Share"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            UserInfo(
                user = detailViewModel.state.collectAsState().value,
                getUserGithubUrl = {
                    githubUrl = it
                })
        }
    }
}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    user: UserDetailState,
    getUserGithubUrl: (String) -> Unit
) {
    when (user) {
        is UserDetailState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }

        is UserDetailState.Success -> {
            getUserGithubUrl(user.data.htmlUrl)
            Column {
                ProfileSection(user = user.data, modifier = modifier)
            }
        }

        is UserDetailState.Error -> {
            Text(text = user.errorMessage)
        }
    }
}

@Composable
fun ProfileSection(user: UserDetail, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = modifier
                    .size(128.dp)
                    .clip(CircleShape),
                model = user.avatarUrl,
                contentDescription = user.name
            )
            Column(modifier = modifier) {
                Text(
                    text = user.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = user.login,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
                user.location?.let { Text(text = it, color = MaterialTheme.colorScheme.onPrimary) }
            }
        }
    }
}