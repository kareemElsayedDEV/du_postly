package com.karem.postly.di

import com.karem.postly.data.repository.LocalPostsRepository
import com.karem.postly.data.repository.RemotePostsRepository
import com.karem.postly.domain.repositories.IPostsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Named("local")
    fun bindLocalPostsRepository(localPostsRepository: LocalPostsRepository): IPostsRepository

    @Binds
    @Named("remote")
    fun bindRemotePostRepository(localPostsRepository: RemotePostsRepository): IPostsRepository
}