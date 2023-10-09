package com.bayutb123.mygithub.domain.repository

import com.bayutb123.mygithub.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    fun getUsers(): Flow<List<User>>
    fun searchUsers(query: String): Flow<List<User>>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)

}