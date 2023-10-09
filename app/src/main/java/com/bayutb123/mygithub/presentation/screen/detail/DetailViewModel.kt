package com.bayutb123.mygithub.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.mygithub.data.source.state.RepositoryState
import com.bayutb123.mygithub.data.source.state.UserDetailState
import com.bayutb123.mygithub.data.source.state.UserState
import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.domain.usecase.DatabaseUseCase
import com.bayutb123.mygithub.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val databaseUseCase: DatabaseUseCase
) : ViewModel() {
    private val _userState = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    private val _repoState = MutableStateFlow<RepositoryState>(RepositoryState.Loading)
    private val _followerState = MutableStateFlow<UserState>(UserState.Loading)
    private val _followingState = MutableStateFlow<UserState>(UserState.Loading)

    val repoState = _repoState.asStateFlow()
    val userState = _userState.asStateFlow()
    val followerState = _followerState.asStateFlow()
    val followingState = _followingState.asStateFlow()

    fun init(userName: String) {
        getUserDetail(userName)
        getUserRepos(userName)
        getUserFollowers(userName)
        getUserFollowing(userName)
    }

    private fun getUserDetail(userName: String) {
        viewModelScope.launch {
            _userState.value = UserDetailState.Loading
            val result = userUseCase.getUserDetail(userName)
            _userState.value = if (result != null) {
                UserDetailState.Success(result)
            } else {
                UserDetailState.Empty("No data can be provided, this might because of rate limit")
            }
        }
    }

    private fun getUserRepos(userName: String) {
        viewModelScope.launch {
            _repoState.value = RepositoryState.Loading
            val result = userUseCase.getUserRepos(userName).sortedByDescending {
                it.updatedAt
            }
            _repoState.value = if (result.isNotEmpty()) {
                RepositoryState.Success(result)
            } else {
                RepositoryState.Empty("No data")
            }
        }
    }

    private fun getUserFollowers(userName: String) {
        viewModelScope.launch {
            _followerState.value = UserState.Loading
            val result = userUseCase.getUserFollowers(userName)
            _followerState.value = if (result.isNotEmpty()) {
                UserState.Success(result)
            } else {
                UserState.Empty("No data")
            }
        }
    }

    private fun getUserFollowing(userName: String) {
        viewModelScope.launch {
            _followingState.value = UserState.Loading
            val result = userUseCase.getUserFollowing(userName)
            _followingState.value = if (result.isNotEmpty()) {
                UserState.Success(result)
            } else {
                UserState.Empty("No data")
            }
        }
    }

    fun deleteUser(user: UserDetail) {
        viewModelScope.launch {
            databaseUseCase.deleteUser(user)
        }
    }

    fun insertUser(user: UserDetail) {
        viewModelScope.launch {
            databaseUseCase.saveUser(user)
        }
    }

    fun getUserState(login: String): Boolean {
        return false
    }
}