package com.bayutb123.mygithub.data.source.remote

import com.bayutb123.mygithub.domain.model.User

sealed class UserState {
    object Loading : UserState()
    data class Success(val data: List<User>) : UserState()
    data class Empty(val message: String) : UserState()
    data class Error(val errorMessage: String) : UserState()
}
