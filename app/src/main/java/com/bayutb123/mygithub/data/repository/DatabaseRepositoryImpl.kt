package com.bayutb123.mygithub.data.repository

import com.bayutb123.mygithub.data.source.local.UsersDao
import com.bayutb123.mygithub.data.utils.DataMapper.Companion.mapUserDetailToUser
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: UsersDao
) : DatabaseRepository {
    override fun getUsers(): Flow<List<User>> {
        return dao.getUsers()
    }

    override fun searchUsers(query: String): Flow<List<User>> {
        return dao.searchUsers(query)
    }

    override suspend fun insertUser(user: UserDetail) {
        dao.insertUser(mapUserDetailToUser(user))
    }

    override suspend fun deleteUser(user: UserDetail) {
        dao.deleteUser(mapUserDetailToUser(user))
    }

    override fun getUser(login: String): Flow<User> {
        return dao.getUser(login)
    }

}