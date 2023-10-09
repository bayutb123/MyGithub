package com.bayutb123.mygithub.domain.usecase

import com.bayutb123.mygithub.domain.model.UserDetail
import com.bayutb123.mygithub.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    fun getAllSavedUsers() = databaseRepository.getUsers()
    fun searchUsers(query: String) = databaseRepository.searchUsers(query)
    suspend fun saveUser(user: UserDetail, currentState: Boolean = false) {
        databaseRepository.insertUser(user, currentState)
    }

    suspend fun deleteUser(user: UserDetail, currentState: Boolean = true) = databaseRepository.deleteUser(user, currentState)

    fun getUser(login: String) = databaseRepository.getUser(login)
}
