package com.bayutb123.mygithub.data.source.state

import com.bayutb123.mygithub.domain.model.Repository

sealed class RepositoryState {
    object Loading : RepositoryState()
    data class Success(val data: List<Repository>) : RepositoryState()
    data class Error(val errorMessage: String) : RepositoryState()
}
