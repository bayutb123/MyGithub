package com.bayutb123.mygithub.domain.model

import androidx.room.Entity

data class UserDetail(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String?,
    val repoUrl: String,
    val blog: String?,
    val company: String?,
    val followers: Int,
    val following: Int,
    val followersUrl: String,
    val followingUrl: String,
    val location: String?
)
