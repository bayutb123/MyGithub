package com.bayutb123.mygithub.domain.usecase

import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    fun getAllSavedUsers() = databaseRepository.getUsers()
    fun searchUsers(query: String) = databaseRepository.searchUsers(query)
    suspend fun saveUser(user: UserDetail) {
        databaseRepository.insertUser(user)
    }

    suspend fun deleteUser(user: UserDetail) = databaseRepository.deleteUser(user )

    fun getUser(login: String) = databaseRepository.getUser(login)
}
