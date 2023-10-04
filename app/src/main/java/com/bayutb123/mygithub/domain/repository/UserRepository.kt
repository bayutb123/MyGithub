package com.bayutb123.mygithub.domain.repository

import com.bayutb123.mygithub.domain.model.Repository
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail

interface UserRepository {

    suspend fun getAllUsers() : List<User>
    suspend fun searchUsers(query: String) : List<User>
    suspend fun getUserDetail(username: String) : UserDetail

    suspend fun getUserRepos(username: String) : List<Repository>

}