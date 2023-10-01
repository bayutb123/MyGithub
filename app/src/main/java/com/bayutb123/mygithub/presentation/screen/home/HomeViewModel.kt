package com.bayutb123.mygithub.presentation.screen.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _users = mutableStateListOf<User>()
    val users: List<User>
        get() = _users

    init {
        getAllUsers()
    }

    fun getAllUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = userUseCase.getAllUsers()
            _users.clear()
            _users.addAll(result)
            Log.d("HomeViewModel", "getAllUsers: ${_users.size}")
        }
    }

    fun searchUsers(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = userUseCase.searchUsers(query)
            _users.clear()
            _users.addAll(result)
            Log.d("HomeViewModel", "searchUsers: ${_users.size}")
        }
    }
}