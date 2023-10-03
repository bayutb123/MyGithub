package com.bayutb123.mygithub.presentation.screen.detail

import androidx.lifecycle.ViewModel
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
    private val _state = MutableStateFlow<UserDetailState>(UserDetailState.Loading)
    val state = _state.asStateFlow()

    fun getUserDetail(userName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = UserDetailState.Loading
            userUseCase.getUserDetail(userName).let {
                _state.value = UserDetailState.Success(it)
            }
        }
    }
}