package com.bayutb123.mygithub.presentation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import coil.transform.Transformation
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.presentation.screen.home.components.SearchBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    var isSearching by remember { mutableStateOf(false) }

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
                        isSearching = it.isNotEmpty()
                        CoroutineScope(homeViewModel.viewModelScope.coroutineContext).launch {
                            delay(3000)
                            if (isSearching) {
                                homeViewModel.searchUsers(it)
                            } else {
                                homeViewModel.getAllUsers()
                            }
                        }
                    }
                )
            }

            LazyColumn {

                items(homeViewModel.users) { user ->
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {

        AsyncImage(model = user.avatarUrl, contentDescription = user.login, modifier = modifier.clip(
            CircleShape).size(64.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop)
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