package com.bayutb123.mygithub.domain.usecase

import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getAllUsers() : List<User>{
        return userRepository.getAllUsers()
    }
}