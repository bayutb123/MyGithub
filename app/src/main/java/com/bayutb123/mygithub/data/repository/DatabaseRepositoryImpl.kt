package com.bayutb123.mygithub.data.repository

import android.util.Log
import com.bayutb123.mygithub.data.source.local.UsersDao
import com.bayutb123.mygithub.data.utils.DataMapper.Companion.saveAndMapUser
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val dao: UsersDao
) : DatabaseRepository {
    override fun getUsers(): Flow<List<User>> {
        Log.d("DatabaseRepositoryImpl", "getUsers")
        return dao.getUsers()
    }

    override fun searchUsers(query: String): Flow<List<User>> {
        Log.d("DatabaseRepositoryImpl", "searchUsers: $query")
        return dao.searchUsers(query)
    }

    override suspend fun insertUser(user: UserDetail) {
        Log.d("DatabaseRepositoryImpl", "insertUser: ${user.login}")
        dao.insertUser(saveAndMapUser(user))
    }

    override suspend fun insertUserFromUser(user: User) {
        Log.d("DatabaseRepositoryImpl", "insertUserFromUser: ${user.login}")
        dao.insertUser(user)
    }

    override suspend fun deleteUser(user: UserDetail) {
        Log.d("DatabaseRepositoryImpl", "deleteUser: ${user.login}")
        dao.deleteUser(saveAndMapUser(user))
    }

    override suspend fun deleteUserFromUser(user: User) {
        Log.d("DatabaseRepositoryImpl", "deleteAllUsers: ${user.login}")
        dao.deleteUser(user)
    }

    override fun getUser(login: String): User {
        return dao.getUser(login)
    }

}