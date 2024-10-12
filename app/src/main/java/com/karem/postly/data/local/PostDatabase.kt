package com.karem.postly.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karem.postly.data.local.daos.CommentDao
import com.karem.postly.data.local.daos.FavoritePostDao
import com.karem.postly.data.local.daos.PostDao
import com.karem.postly.data.local.daos.UserDao
import com.karem.postly.data.local.entities.Comment
import com.karem.postly.data.local.entities.Favorite
import com.karem.postly.data.local.entities.Post
import com.karem.postly.data.local.entities.User

@Database(entities = [Post::class, Comment::class, User::class, Favorite::class], version = 3)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun userDao(): UserDao
    abstract fun favoritePostDao(): FavoritePostDao
}