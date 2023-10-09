package com.bayutb123.mygithub.presentation.screen.home.recommendation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.mygithub.data.source.state.UserState
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UserState>(UserState.Loading)
    val state = _state.asStateFlow()
    private val _searchState = MutableStateFlow<UserState>(UserState.Loading)
    val searchState = _searchState.asStateFlow()

    fun getAllUsers(cacheList : List<User>) {
        viewModelScope.launch {
            if (cacheList.isNotEmpty()) {
                _state.value = UserState.Success(cacheList)
                Log.d("RecommendationViewModel", "getAllUsers: cacheList is not empty")
            } else {
                _state.value = UserState.Loading
                val result = userUseCase.getAllUsers()
                _state.value = if (result.isNotEmpty()) {
                    UserState.Success(result)
                } else {
                    UserState.Empty("No data can be provided, this might because of rate limit")
                }
                Log.d("RecommendationViewModel", "getAllUsers: cacheList is empty")
            }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _searchState.value = UserState.Loading
            val result = userUseCase.searchUsers(query)
            _searchState.value = if (result.isNotEmpty()) {
                UserState.Success(result)
            } else {
                UserState.Empty("No data can be provided, this might because of rate limit")
            }
        }
    }
}