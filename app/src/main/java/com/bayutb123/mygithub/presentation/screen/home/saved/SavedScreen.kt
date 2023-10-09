package com.bayutb123.mygithub.presentation.screen.home.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.mygithub.data.source.state.UserState
import com.bayutb123.mygithub.presentation.screen.components.LoadingAnimation
import com.bayutb123.mygithub.presentation.screen.home.recommendation.UserItem

@Composable
fun SavedScreen(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
    viewModel: SavedViewModel = hiltViewModel()
) {
    when (val state = viewModel.state.collectAsState().value) {
        is UserState.Success -> {
            UserList(
                modifier = modifier,
                state = state,
                onClick = onItemClick,
            )
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
fun UserList(
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
