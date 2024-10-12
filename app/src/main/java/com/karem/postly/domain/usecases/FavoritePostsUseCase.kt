package com.karem.postly.domain.usecases

import com.karem.postly.core.Resource
import com.karem.postly.data.cached.SettingsPref
import com.karem.postly.data.local.daos.FavoritePostDao
import com.karem.postly.data.local.daos.PostDao
import com.karem.postly.data.repository.PostsRepository
import com.karem.postly.domain.models.CommentDto
import com.karem.postly.domain.models.PostDto
import com.karem.postly.domain.repositories.IPostsRepository
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject
import javax.inject.Named

class FavoritePostsUseCase @Inject constructor(
    @Named("local") private val localPostsRepository: IPostsRepository,
    private val getFavoriteUseCase: GetFavoriteUseCase
) {

    operator fun invoke() = flow {
        emit(Resource.Loading)
        val posts = localPostsRepository.getPosts().filter {
            getFavoriteUseCase().contains(it.id)
        }


        try {
            val postsDto = posts.map { post ->
                val comments = localPostsRepository.getCommentsForPost(post.id)
                val postUser = localPostsRepository.getUserWithId(post.userId) ?: return@map null

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