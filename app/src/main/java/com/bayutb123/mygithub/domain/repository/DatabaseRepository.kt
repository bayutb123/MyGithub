package com.bayutb123.mygithub.domain.repository

import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    fun getUsers(): Flow<List<User>>
    fun searchUsers(query: String): Flow<List<User>>
    suspend fun insertUser(user: UserDetail)
    suspend fun insertUserFromUser(user: User)
    suspend fun deleteUser(user: UserDetail)

    suspend fun deleteUserFromUser(user: User)

    fun getUser(login: String):User

}