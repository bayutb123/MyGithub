package com.bayutb123.mygithub.domain.repository

import com.bayutb123.mygithub.domain.model.User

interface UserRepository {

    suspend fun getAllUsers() : List<User>
    suspend fun searchUsers(query: String) : List<User>

}