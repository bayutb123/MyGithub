package com.bayutb123.mygithub.data.source.remote.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface ApiService {

    @GET("users")
    suspend fun getAllUsers(
        @Header("Authorization") token: String
    ) : Response<List<UserResponse>>

}