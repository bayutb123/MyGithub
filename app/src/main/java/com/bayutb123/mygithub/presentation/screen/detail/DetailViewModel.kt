package com.bayutb123.mygithub.presentation.screen.detail

import androidx.lifecycle.ViewModel
import com.bayutb123.mygithub.data.source.state.RepositoryState
import com.bayutb123.mygithub.data.source.state.UserDetailState
import com.bayutb123.mygithub.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _userState = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    private val _repoState = MutableStateFlow<RepositoryState>(RepositoryState.Loading)
    val repoState = _repoState.asStateFlow()
    val userState = _userState.asStateFlow()

    fun getUserDetail(userName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _userState.value = UserDetailState.Loading
            userUseCase.getUserDetail(userName).let {
                _userState.value = UserDetailState.Success(it)
            }
        }
    }

    fun getUserRepos(userName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _repoState.value = RepositoryState.Loading
            userUseCase.getUserRepos(userName).let {
                _repoState.value = RepositoryState.Success(it)
            }
        }
    }
}