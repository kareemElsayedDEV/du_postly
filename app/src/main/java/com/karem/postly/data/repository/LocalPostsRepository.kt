package com.karem.postly.data.repository

import com.karem.postly.data.local.daos.CommentDao
import com.karem.postly.data.local.daos.PostDao
import com.karem.postly.data.local.daos.UserDao
import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Post
import com.karem.postly.data.local.entities.User
import com.karem.postly.domain.repositories.IPostsRepository
import javax.inject.Inject

class LocalPostsRepository @Inject constructor(
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val userDao: UserDao
) : IPostsRepository {
    override suspend fun getPosts(): List<Post> {
        return postDao.getAllPosts()
    }

    override suspend fun getCommentsForPost(postId: Int): List<Comment> {
        return commentDao.getCommentsForPost(postId)
    }

    override suspend fun getUserWithId(userId: Int): User {
        return userDao.getUserById(userId) ?: User(-1, "Unknown")
    }

}