package com.bayutb123.mygithub.domain.model

data class Repository(
    val id : Int,
    val name : String?,
    val fullName : String,
    val createdAt : String,
    val updatedAt : String,
    val archived : Boolean,
    val lisence: Lisence,
    val htmlUrl : String,
)

data class Lisence(
    val key : String?,
    val name : String?,
    val url : String?
)