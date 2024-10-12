package com.karem.postly.di

import android.content.Context
import androidx.room.Room
import com.karem.postly.data.local.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providePostDatabase(@ApplicationContext application: Context): PostDatabase {
        return Room
            .databaseBuilder(application, PostDatabase::class.java, "posts_db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun commentDao(postDatabase: PostDatabase) = postDatabase.commentDao()

    @Provides
    fun postDao(postDatabase: PostDatabase) = postDatabase.postDao()

    @Provides
    fun userDao(postDatabase: PostDatabase) = postDatabase.userDao()

    @Provides
    fun favoriteDao(postDatabase: PostDatabase) = postDatabase.favoritePostDao()


}