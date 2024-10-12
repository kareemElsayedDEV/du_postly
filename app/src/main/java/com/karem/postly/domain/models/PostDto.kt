package com.karem.postly.domain.models


data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
    val userName: String,
    val comments: List<CommentDto>
)