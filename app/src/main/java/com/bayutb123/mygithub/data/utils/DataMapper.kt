package com.bayutb123.mygithub.data.utils

import com.bayutb123.mygithub.data.source.remote.response.UserResponse
import com.bayutb123.mygithub.domain.model.User

class DataMapper {
    companion object {
        fun mapUserResponseToDomain(response: List<UserResponse>): List<User> {
            val listUser = ArrayList<User>()
            response.forEach {
                val user = User(
                    id = it.id,
                    login = it.login,
                    avatarUrl = it.avatar_url,
                    followersUrl = it.followers_url,
                    followingUrl = it.following_url,
                    reposUrl = it.repos_url
                )
                listUser.add(user)
            }
            return listUser
        }
    }
}