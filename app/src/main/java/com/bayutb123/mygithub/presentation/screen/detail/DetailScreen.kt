package com.bayutb123.mygithub.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bayutb123.mygithub.data.source.state.RepositoryState
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
    detailViewModel.getUserRepos(userName)
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
                user = detailViewModel.userState.collectAsState().value,
                getUserGithubUrl = {
                    githubUrl = it
                })
            RepositorySection(
                repositoryState = detailViewModel.repoState.collectAsState().value
            )
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
            Box(modifier = modifier.fillMaxWidth().height(172.dp).background(color = MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
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
fun RepositorySection(
    modifier: Modifier = Modifier,
    repositoryState: RepositoryState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Repositories",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge
        )
    }
    when (repositoryState){
        is RepositoryState.Loading -> {
            Box(modifier = modifier.fillMaxWidth().height(128.dp), contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }
        is RepositoryState.Success -> {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(repositoryState.data) {
                    Card(
                        modifier = modifier
                            .width(width = 256.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Text(text = it.fullName, style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            if (it.archived) {
                                Text(text = "Archived", style = MaterialTheme.typography.bodySmall)
                            }
                            Text(text = it.lisence.name ?: "Not lisenced", style = MaterialTheme.typography.bodyMedium)
                            Text(text = it.createdAt, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
        is RepositoryState.Error -> {
            Text(text = repositoryState.errorMessage)
        }
    }
}


@Composable
fun ProfileSection(user: UserDetail, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(172.dp)
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
                    text = user.name ?: user.login,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                user.location?.let { Text(text = it, color = MaterialTheme.colorScheme.onPrimary) }
            }
        }
    }
}