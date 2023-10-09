package com.bayutb123.mygithub.data.utils

import com.bayutb123.mygithub.data.source.remote.response.RepositoryResponse
import com.bayutb123.mygithub.data.source.remote.response.SearchUserResponse
import com.bayutb123.mygithub.data.source.remote.response.UserDetailResponse
import com.bayutb123.mygithub.data.source.remote.response.UserResponse
import com.bayutb123.mygithub.domain.model.Lisence
import com.bayutb123.mygithub.domain.model.Repository
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
                repoUrl = response.repos_url,
                blog = response.blog,
                company = response.company,
                followers = response.followers,
                followersUrl = response.followers_url,
                following = response.following,
                followingUrl = response.following_url,
                location = response.location
            )
        }

        fun mapRepositoryResponseToDomain(response: List<RepositoryResponse>) : List<Repository> {
            val listRepository = mutableListOf<Repository>()
            response.forEach{
                listRepository.add(
                    Repository(
                        id = it.id,
                        name = it.name,
                        fullName = it.full_name,
                        createdAt = it.created_at,
                        updatedAt = it.updated_at,
                        archived = it.archived,
                        lisence = Lisence(
                            key = it.license?.key,
                            name = it.license?.name,
                            url = it.license?.url
                        ),
                        htmlUrl = it.html_url
                    )
                )
            }
            return listRepository
        }

        fun mapUserDetailToUser(userDetail: UserDetail) : User {
            return User(
                id = userDetail.id,
                login = userDetail.login,
                apiUrl = userDetail.htmlUrl,
                avatarUrl = userDetail.avatarUrl,
                followersUrl = userDetail.followersUrl,
                followingUrl = userDetail.followingUrl,
                reposUrl = userDetail.repoUrl
            )
        }
    }
}