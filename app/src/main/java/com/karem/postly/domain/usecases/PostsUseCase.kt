package com.karem.postly.domain.usecases

import com.karem.postly.core.Resource
import com.karem.postly.data.local.daos.FavoritePostDao
import com.karem.postly.data.local.daos.PostDao
import com.karem.postly.data.repository.PostsRepository
import com.karem.postly.domain.models.CommentDto
import com.karem.postly.domain.models.PostDto
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class PostsUseCase @Inject constructor(
    private val postsRepository: PostsRepository
) {

    operator fun invoke(currentPage: Int = 1) = flow {
        emit(Resource.Loading)
        val posts = postsRepository.getPosts(currentPage);
        if (posts == null) {
            emit(Resource.Error(-20))
            return@flow
        }

        try {
            val postsDto = posts.map { post ->
                val comments = postsRepository.getCommentsForPost(post.id)
                val postUser = postsRepository.getUserWithId(post.userId) ?: return@map null

                PostDto(
                    post.id,
                    post.title,
                    post.body,
                    postUser.name,
                    comments.map {
                        CommentDto(it.name, it.email, it.body)
                    }
                )
            }.requireNoNulls()

            emit(Resource.Success(postsDto))
        } catch (e: IOException) {
            emit(Resource.Error(message = "please check you internet connection", exception = e))
        } catch (e: Exception) {
            emit(Resource.Error(exception = e))
        }


    }

}