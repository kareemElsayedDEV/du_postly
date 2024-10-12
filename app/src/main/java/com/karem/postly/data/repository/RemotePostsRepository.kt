package com.karem.postly.data.repository

import com.karem.postly.data.local.daos.CommentDao
import com.karem.postly.data.local.daos.PostDao
import com.karem.postly.data.local.daos.UserDao
import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Post
import com.karem.postly.data.local.entities.User
import com.karem.postly.data.remote.ApiServices
import com.karem.postly.domain.repositories.IPostsRepository
import javax.inject.Inject

class RemotePostsRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val userDao: UserDao
) : IPostsRepository {
    override suspend fun getPosts(): List<Post> {
        val data = apiServices.getPosts();
        postDao.insertAllPosts(data)
        return data
    }

    override suspend fun getCommentsForPost(postId: Int): List<Comment> {
        val data = apiServices.getComments(postId)
        commentDao.insertAllComments(data)
        return data;
    }

    override suspend fun getUserWithId(userId: Int): User {
        val data = apiServices.getUserById(userId)
        userDao.insertUser(data)
        return data
    }

}