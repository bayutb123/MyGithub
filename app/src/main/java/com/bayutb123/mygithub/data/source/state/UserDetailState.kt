package com.bayutb123.mygithub.data.source.state

import com.bayutb123.mygithub.domain.model.UserDetail

sealed class UserDetailState {
    object Loading : UserDetailState()
    data class Success(val data: UserDetail) : UserDetailState()
    data class Error(val errorMessage: String) : UserDetailState()

}
