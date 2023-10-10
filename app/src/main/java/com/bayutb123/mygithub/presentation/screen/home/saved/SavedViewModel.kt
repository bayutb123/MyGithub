package com.bayutb123.mygithub.presentation.screen.home.saved

import androidx.lifecycle.ViewModel
import com.bayutb123.mygithub.data.source.state.UserState
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.domain.usecase.DatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val savedUserUseCase: DatabaseUseCase
) :ViewModel() {
    private val _state = MutableStateFlow<UserState>(UserState.Loading)
    val state = _state
    init {
        CoroutineScope(Dispatchers.IO).launch {
            getAllSavedUsers()
        }
    }

    private suspend fun getAllSavedUsers() {
        savedUserUseCase.getAllSavedUsers().collect {
            _state.value = if (it.isNotEmpty()) {
                UserState.Success(it)
            } else {
                UserState.Empty("No data can be provided, this might because of rate limit")
            }
        }

    }

    fun searchUsers(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            savedUserUseCase.searchUsers(query).collect {
                _state.value = if (it.isNotEmpty()) {
                    UserState.Success(it)
                } else {
                    UserState.Empty("No data can be provided, this might because of rate limit")
                }
            }
        }
    }

    fun deleteUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            savedUserUseCase.deleteUserFromUser(user)
        }
    }

}