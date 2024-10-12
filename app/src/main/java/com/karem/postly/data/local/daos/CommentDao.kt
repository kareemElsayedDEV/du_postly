package com.karem.postly.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karem.postly.data.local.entities.Comment
import dagger.Provides

@Dao
interface CommentDao {
    @Insert
    suspend fun insert(comment: Comment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllComments(comments: List<Comment>)

    @Query("SELECT * FROM comments where postId = :postId")
    suspend fun getCommentsForPost(postId: Int): List<Comment>

}