package com.karem.postly.data.remote

import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Post
import com.karem.postly.data.local.entities.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @GET("/posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): List<Post>

    @GET("/comments")
    suspend fun getComments(@Query("postId") postId: Int = 1): List<Comment>

    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") userId: Int): User
}