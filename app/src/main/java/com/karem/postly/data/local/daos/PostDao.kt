package com.karem.postly.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(posts: List<Post>)

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<Post>


}