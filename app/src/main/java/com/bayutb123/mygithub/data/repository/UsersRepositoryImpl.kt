package com.bayutb123.mygithub.data.repository

import com.bayutb123.mygithub.BuildConfig
import com.bayutb123.mygithub.data.source.remote.ApiService
import com.bayutb123.mygithub.data.utils.DataMapper
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.repository.UserRepository
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {
    private val token = BuildConfig.API_KEY
    override suspend fun getAllUsers(): List<User> {

        try {
            val response = apiService.getAllUsers(token)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    return DataMapper.mapUserResponseToDomain(data)
                }
            }
            throw Exception("Error ${response.code()}")
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }

    }

    override suspend fun searchUsers(query: String): List<User> {
        try {
            val response = apiService.searchUsers(token, query)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    return DataMapper.mapUserSearchResponseToDomain(data)
                }
            } else if (response.code() == 403) {
                throw Exception("API rate limit exceeded")
            }
            throw Exception("Error ${response.code()}")
        } catch (e: Exception) {
            throw Exception(e.message.toString())
        }
    }
}