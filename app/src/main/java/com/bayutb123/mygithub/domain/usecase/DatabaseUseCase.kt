package com.bayutb123.mygithub.domain.usecase

import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseUseCase @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    fun getAllSavedUsers() = databaseRepository.getUsers()
    fun searchUsers(query: String) = databaseRepository.searchUsers(query)
    suspend fun saveUser(user: User) {
        databaseRepository.insertUser(user)
    }

    suspend fun deleteUser(user: User) = databaseRepository.deleteUser(user)
}
