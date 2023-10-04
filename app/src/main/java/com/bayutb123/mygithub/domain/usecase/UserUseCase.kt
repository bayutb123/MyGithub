package com.bayutb123.mygithub.domain.usecase

import com.bayutb123.mygithub.domain.model.Repository
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getAllUsers() : List<User>{
        return userRepository.getAllUsers()
    }

    suspend fun searchUsers(query: String) : List<User>{
        return userRepository.searchUsers(query)
    }

    suspend fun getUserDetail(username: String) : UserDetail? {
        return userRepository.getUserDetail(username)
    }

    suspend fun getUserRepos(username: String) : List<Repository> {
        return userRepository.getUserRepos(username)
    }
}