package com.karem.postly.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Favorite
import com.karem.postly.data.local.entities.Post
import com.karem.postly.data.local.entities.User

@Dao
interface FavoritePostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite where email = :email")
    suspend fun getAllFavorite(email: String): List<Favorite>

    @Query("SELECT * FROM favorite WHERE postId = :postId")
    suspend fun getFavoriteById(postId: Int): Favorite?
}