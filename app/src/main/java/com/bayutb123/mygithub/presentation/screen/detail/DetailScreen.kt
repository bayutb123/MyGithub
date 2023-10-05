package com.bayutb123.mygithub.presentation.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bayutb123.mygithub.data.source.state.RepositoryState
import com.bayutb123.mygithub.data.source.state.UserDetailState
import com.bayutb123.mygithub.data.source.state.UserState
import com.bayutb123.mygithub.data.utils.DateFormatter
import com.bayutb123.mygithub.domain.model.Repository
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.presentation.screen.components.LoadingAnimation

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    userName: String
) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    val clipboardManager = LocalClipboardManager.current
    detailViewModel.getUserDetail(userName)
    detailViewModel.getUserRepos(userName)
    detailViewModel.getUserFollowers(userName)
    detailViewModel.getUserFollowing(userName)
    var githubUrl = ""

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    clipboardManager.setText(AnnotatedString(githubUrl))
                }) {
                Text(text = "Copy Github URL")
                Spacer(modifier = Modifier.width(4.dp))
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
            TabSection(
                modifier = modifier,
                followerState = detailViewModel.followerState.collectAsState().value,
                followingState = detailViewModel.followingState.collectAsState().value
            )
        }
    }
}

@Composable
fun TabSection(
    modifier: Modifier = Modifier,
    followerState: UserState,
    followingState: UserState
) {
    var selectedTabRow by remember {
        mutableIntStateOf(0)
    }
    TabView(
        modifier = modifier,
        selectedTab = selectedTabRow,
        onTabSelected = {
            selectedTabRow = it
        },
        followersState = followerState,
        followingState = followingState
    )
}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    user: UserDetailState,
    getUserGithubUrl: (String) -> Unit
) {
    when (user) {
        is UserDetailState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(172.dp)
                    .background(color = MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        }

        is UserDetailState.Success -> {
            getUserGithubUrl(user.data.htmlUrl)
            Column {
                ProfileSection(user = user.data, modifier = modifier)
            }
        }

        is UserDetailState.Empty -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(172.dp)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp), contentAlignment = Alignment.Center
            ) {
                Text(text = user.message, textAlign = TextAlign.Center)
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
    when (repositoryState) {
        is RepositoryState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(72.dp), contentAlignment = Alignment.Center
            ) {
                LoadingAnimation()
            }
        }

        is RepositoryState.Success -> {
            Column {
                RepositoryRow(
                    modifier = modifier,
                    repos = repositoryState.data
                )
            }
        }

        is RepositoryState.Empty -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .padding(horizontal = 16.dp), contentAlignment = Alignment.Center
            ) {
                Text(text = repositoryState.message, textAlign = TextAlign.Center)
            }
        }

        is RepositoryState.Error -> {
            Text(text = repositoryState.errorMessage)
        }
    }
}

@Composable
fun RepositoryRow(
    modifier: Modifier = Modifier,
    repos: List<Repository>
) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(repos) {
            Card(
                modifier = modifier
                    .width(width = 256.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 0.dp
                )
            ) {
                Box(

                ) {
                    it.lisence.name?.let { name ->
                        Box(
                            modifier = modifier
                                .width(128.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(bottomEnd = 4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {

                            Text(
                                text = name,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 24.dp, bottom = 16.dp, end = 16.dp),
                    ) {
                        Text(
                            text = it.fullName,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Last updated at ${DateFormatter.dateFormat(it.updatedAt)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
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
                Row {
                    Icon(
                        imageVector = Icons.Default.PersonOutline,
                        contentDescription = null,
                        modifier = modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "${user.followers} followers",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.PersonOutline,
                        contentDescription = null,
                        modifier = modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "${user.following} followings",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun Connections(followers: List<User>, modifier: Modifier) {
    LazyColumn(contentPadding = PaddingValues(vertical =8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(followers) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {  }
                    .padding(horizontal = 16.dp),

                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = it.avatarUrl, contentDescription =it.login, modifier = modifier
                        .clip(
                            CircleShape
                        )
                        .size(64.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
                Column(modifier = modifier.padding(start = 16.dp)) {
                    Text(
                        text = it.login,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                }
            }
        }
    }
}

@Composable
@UiComposable
fun TabView(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    followersState: UserState,
    followingState: UserState
) {
    TabRow(
        selectedTabIndex = selectedTab,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = MaterialTheme.colorScheme.primary
            )
        },
        contentColor = MaterialTheme.colorScheme.onSurface,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.fillMaxWidth().padding(top = 8.dp),
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                text = { Text(text = "Followers") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                text = { Text(text = "Following") }
            )


        }
        when (selectedTab) {
            0 -> {
                when (followersState) {
                    is UserState.Loading -> {
                        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingAnimation()
                        }
                    }
                    is UserState.Success -> {
                        Connections(followers = followersState.data, modifier = modifier)
                    }
                    is UserState.Empty -> {
                        Box(modifier = modifier.fillMaxSize().padding(horizontal = 8.dp), contentAlignment = Alignment.Center) {
                            Text(text = followersState.message, textAlign = TextAlign.Center)
                        }
                    }
                    else -> {
                        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingAnimation()
                        }
                    }
                }
            }
            1 -> {
                when (followingState) {
                    is UserState.Loading -> {
                        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingAnimation()
                        }
                    }
                    is UserState.Success -> {
                        Connections(followers = followingState.data, modifier = modifier)
                    }
                    is UserState.Empty -> {
                        Box(modifier = modifier.fillMaxSize().padding(horizontal = 8.dp), contentAlignment = Alignment.Center) {
                            Text(text = followingState.message, textAlign = TextAlign.Center)
                        }
                    }
                    else -> {
                        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LoadingAnimation()
                        }
                    }
                }
            }
        }

}