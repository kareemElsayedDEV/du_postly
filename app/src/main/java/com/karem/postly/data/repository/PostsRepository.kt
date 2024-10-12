package com.karem.postly.data.repository

import android.app.Application
import com.karem.postly.core.hasInternetConnection
import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Post
import com.karem.postly.data.local.entities.User
import com.karem.postly.domain.repositories.IPostsRepository
import javax.inject.Inject
import javax.inject.Named

class PostsRepository @Inject constructor(
    @Named("local") private val localPostsRepository: IPostsRepository,
    @Named("remote") private val remotePostsRepository: IPostsRepository,
    private val application: Application
) {
    private val limit = 10;

    suspend fun getPosts(currentPage: Int = 1): List<Post>? {
        val startIndex = limit * (currentPage - 1);
        val endIndex = limit * currentPage

        val allPosts: List<Post> = if (hasInternetConnection(application)) {
            remotePostsRepository.getPosts()
        } else {
            localPostsRepository.getPosts()
        }

        if (allPosts.size < endIndex) return null;



        return allPosts.subList(startIndex, endIndex)
    }

    suspend fun getCommentsForPost(postId: Int): List<Comment> {
        val allComments: List<Comment> = if (hasInternetConnection(application)) {
            remotePostsRepository.getCommentsForPost(postId)
        } else {
            localPostsRepository.getCommentsForPost(postId)
        }
        return allComments
    }

    suspend fun getUserWithId(userId: Int): User? {
        val user: User? = if (hasInternetConnection(application)) {
            remotePostsRepository.getUserWithId(userId)
        } else {
            localPostsRepository.getUserWithId(userId)
        }

        return user
    }


}