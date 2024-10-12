package com.karem.postly.domain.repositories

import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Post
import com.karem.postly.data.local.entities.User

interface IPostsRepository {
    suspend fun getPosts(): List<Post>

    suspend fun getCommentsForPost(postId: Int = 1): List<Comment>

    suspend fun getUserWithId(userId: Int = 1): User?
}