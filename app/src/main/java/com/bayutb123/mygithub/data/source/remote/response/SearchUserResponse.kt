package com.bayutb123.mygithub.data.source.remote.response

data class SearchUserResponse(
    val incomplete_results: Boolean,
    val items: List<UserResponse>,
    val total_count: Int
)