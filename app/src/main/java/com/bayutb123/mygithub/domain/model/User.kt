package com.bayutb123.mygithub.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["id"], tableName = "users")
data class User(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "apiUrl")
    val apiUrl: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "followers_url")
    val followersUrl: String,
    @ColumnInfo(name = "following_url")
    val followingUrl: String,
    @ColumnInfo(name = "repos_url")
    val reposUrl: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)