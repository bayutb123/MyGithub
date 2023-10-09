package com.bayutb123.mygithub.presentation.screen.home.recommendation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Commit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.bayutb123.mygithub.data.source.state.UserState
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.presentation.screen.components.LoadingAnimation
import com.bayutb123.mygithub.presentation.screen.components.SearchBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RecommendationScreen(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
) {
    val homeViewModel = hiltViewModel<RecommendationViewModel>()
    var isSearching by remember { mutableStateOf(false) }
    var cacheList by rememberSaveable {
        mutableStateOf(
            listOf<User>()
        )
    }

    homeViewModel.getAllUsers(cacheList)

    Scaffold(
        topBar = {
            Column(modifier.padding(horizontal = 16.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedVisibility(visible = !isSearching) {
                    Icon(imageVector = Icons.Rounded.Commit, contentDescription = null, modifier = modifier
                        .padding(bottom = 4.dp)
                        .size(48.dp), tint = MaterialTheme.colorScheme.primary)
                }
                SearchBar(
                    modifier = modifier.fillMaxWidth(),
                    onSearch = {
                        isSearching = it.isNotEmpty()
                        CoroutineScope(homeViewModel.viewModelScope.coroutineContext).launch {
                            if (isSearching) {
                                homeViewModel.searchUsers(it)
                            } else {
                                homeViewModel.getAllUsers(cacheList)
                            }
                        }
                    },
                )
            }
        }
    ) { paddingValue ->
        Column(
            modifier
                .padding(paddingValue)
                .fillMaxWidth()
        ) {

            AnimatedVisibility(visible = !isSearching) {
                UserList(state = homeViewModel.state.collectAsState().value, onSuccessLoad = {
                    cacheList = it
                }, onClick = {
                    onItemClick(it)
                })
            }
            AnimatedVisibility(visible = isSearching) {
                SearchUserList(state = homeViewModel.searchState.collectAsState().value, onClick = {
                    onItemClick(it)
                })
            }

        }
    }
}

@Composable
fun UserList(
    modifier: Modifier = Modifier,
    state: UserState,
    onClick: (String) -> Unit,
    onSuccessLoad: (List<User>) -> Unit
) {
    when (state) {
        is UserState.Success -> {
            onSuccessLoad(state.data)
            LazyColumn(modifier = modifier.fillMaxWidth()) {
                items(state.data) { user ->
                    UserItem(user = user, onClick = {
                        onClick(user.login)
                    })
                }
            }
        }

        is UserState.Empty -> {
            Box(modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
                Text(text = state.message, textAlign = TextAlign.Center)
            }
        }

        is UserState.Error -> {
            Text(text = state.errorMessage)
        }

        is UserState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }
    }
}

@Composable
fun SearchUserList(
    modifier: Modifier = Modifier,
    state: UserState,
    onClick: (String) -> Unit,
) {
    when (state) {
        is UserState.Success -> {

            LazyColumn(modifier = modifier.fillMaxWidth()) {
                items(state.data) { user ->
                    UserItem(user = user, onClick = {
                        onClick(user.login)
                    })
                }
            }
        }

        is UserState.Empty -> {
            Box(modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), contentAlignment = Alignment.Center) {
                Text(text = state.message, textAlign = TextAlign.Center)
            }
        }

        is UserState.Error -> {
            Text(text = state.errorMessage)
        }

        is UserState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingAnimation()
            }
        }
    }
}

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    user: User,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = user.avatarUrl, contentDescription = user.login, modifier = modifier
                .clip(
                    CircleShape
                )
                .size(64.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Column(modifier = modifier.padding(start = 16.dp)) {
            Text(
                text = user.login,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Follower : ${user.followersUrl.length}")
                Text(text = "Following : ${user.followingUrl.length}")
            }
        }
    }

}