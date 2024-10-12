package com.karem.postly.di;

import android.content.Context
import androidx.room.Room
import com.karem.postly.data.cached.SettingsPref
import com.karem.postly.data.local.PostDatabase
import com.karem.postly.data.remote.ApiServices
import com.karem.postly.data.repository.PostsRepository
import com.karem.postly.domain.usecases.IsUserLoggedInUseCase
import com.karem.postly.domain.usecases.LoginUseCase
import com.karem.postly.domain.usecases.LogoutUseCase
import com.karem.postly.domain.usecases.PostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun getPostsService(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun getSettingsSharedPref(@ApplicationContext application: Context): SettingsPref {
        val pref = SettingsPref();
        pref.initSettings(application)
        return pref
    }


    @Provides
    fun getCheckUseCase(settingsPref: SettingsPref) = IsUserLoggedInUseCase(settingsPref)


    @Provides
    fun loginUseCase(settingsPref: SettingsPref) = LoginUseCase(settingsPref)


    @Provides
    fun logoutUseCase(settingsPref: SettingsPref) = LogoutUseCase(settingsPref)


    @Provides
    fun postsUseCase(postsRepository: PostsRepository) = PostsUseCase(postsRepository)


}