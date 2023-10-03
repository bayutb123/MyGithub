package com.bayutb123.mygithub.data.utils

import com.bayutb123.mygithub.data.source.remote.response.SearchUserResponse
import com.bayutb123.mygithub.data.source.remote.response.UserDetailResponse
import com.bayutb123.mygithub.data.source.remote.response.UserResponse
import com.bayutb123.mygithub.domain.model.User
import com.bayutb123.mygithub.domain.model.UserDetail

class DataMapper {
    companion object {
        fun mapUserResponseToDomain(response: List<UserResponse>): List<User> {
            val listUser = ArrayList<User>()
            response.forEach {
                val user = User(
                    id = it.id,
                    login = it.login,
                    apiUrl = it.url,
                    avatarUrl = it.avatar_url,
                    followersUrl = it.followers_url,
                    followingUrl = it.following_url,
                    reposUrl = it.repos_url
                )
                listUser.add(user)
            }
            return listUser
        }

        fun mapUserSearchResponseToDomain(response: SearchUserResponse) : List<User> {
            val listUser = ArrayList<User>()
            response.items.forEach {
                val user = User(
                    id = it.id,
                    login = it.login,
                    apiUrl = it.url,
                    avatarUrl = it.avatar_url,
                    followersUrl = it.followers_url,
                    followingUrl = it.following_url,
                    reposUrl = it.repos_url
                )
                listUser.add(user)
            }
            return listUser
        }

        fun mapUserDetailResponseToDomain(response: UserDetailResponse): UserDetail {
            return UserDetail(
                id = response.id,
                login = response.login,
                avatarUrl = response.avatar_url,
                htmlUrl = response.html_url,
                name = response.name,
                blog = response.blog,
                company = response.company,
                followersUrl = response.followers,
                followingUrl = response.following,
                location = response.location
            )
        }
    }
}