package com.bayutb123.mygithub.domain.model

data class User(
    val id: Int,
    val login: String,
    val apiUrl: String,
    val avatarUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val reposUrl: String,
)