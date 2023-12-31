package com.bayutb123.mygithub.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.mygithub.data.source.state.UserState
import com.bayutb123.mygithub.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UserState>(UserState.Loading)
    val state = _state.asStateFlow()

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        viewModelScope.launch {
            _state.value = UserState.Loading
            val result = userUseCase.getAllUsers()
            _state.value = if (result.isNotEmpty()) {
                UserState.Success(result)
            } else {
                UserState.Empty("No data can be provided, this might because of rate limit")
            }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _state.value = UserState.Loading
            val result = userUseCase.searchUsers(query)
            _state.value = if (result.isNotEmpty()) {
                UserState.Success(result)
            } else {
                UserState.Empty("No data can be provided, this might because of rate limit")
            }
        }
    }
}