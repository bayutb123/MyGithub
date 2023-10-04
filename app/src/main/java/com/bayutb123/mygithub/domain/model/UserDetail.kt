package com.bayutb123.mygithub.domain.model

data class UserDetail(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String?,
    val repoUrl: String,
    val blog: String?,
    val company: String?,
    val followersUrl: Int,
    val followingUrl: Int,
    val location: String?
)
