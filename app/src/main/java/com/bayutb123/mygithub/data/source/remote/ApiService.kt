package com.bayutb123.mygithub.data.source.remote

import com.bayutb123.mygithub.data.source.remote.response.RepositoryResponse
import com.bayutb123.mygithub.data.source.remote.response.SearchUserResponse
import com.bayutb123.mygithub.data.source.remote.response.UserDetailResponse
import com.bayutb123.mygithub.data.source.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("users")
    suspend fun getAllUsers(
        @Header("Authorization") token: String
    ) : Response<List<UserResponse>>

    @GET("search/users")
    @Headers("Accept: application/json")
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 5,
    ) : Response<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Accept: application/json")
    suspend fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ) : Response<UserDetailResponse>

    @GET("users/{username}/repos")
    @Headers("Accept: application/json")
    suspend fun getUserRepos(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ) : Response<List<RepositoryResponse>>

    @GET("users/{username}/followers")
    @Headers("Accept: application/json")
    suspend fun getUserFollowers(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ) : Response<List<UserResponse>>

    @GET("users/{username}/following")
    @Headers("Accept: application/json")
    suspend fun getUserFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ) : Response<List<UserResponse>>

}