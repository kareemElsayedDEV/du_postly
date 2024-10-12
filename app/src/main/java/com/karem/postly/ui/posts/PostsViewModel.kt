package com.karem.postly.ui.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.karem.postly.core.Resource
import com.karem.postly.data.local.daos.PostDao
import com.karem.postly.data.local.entities.Favorite
import com.karem.postly.data.local.entities.Post
import com.karem.postly.domain.models.PostDto
import com.karem.postly.domain.usecases.GetFavoriteUseCase
import com.karem.postly.domain.usecases.PostsUseCase
import com.karem.postly.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsUseCase: PostsUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val postsMutableLiveData = MutableLiveData<List<PostDto>>(emptyList());
    val postsLiveData: LiveData<List<PostDto>> = postsMutableLiveData;

    private val favoriteMutableLiveData = MutableLiveData<List<Int>>();
    val favoriteLiveData: LiveData<List<Int>> = favoriteMutableLiveData;

    private val postsLoadingMutableLiveData = MutableLiveData<Boolean>();
    val postsLoadingLiveData: LiveData<Boolean> = postsLoadingMutableLiveData;

    private val postsLoadingErrorMutableLiveData = MutableLiveData<Resource.Error>();
    val postsErrorLiveData: LiveData<Resource.Error> = postsLoadingErrorMutableLiveData;


    private var currentPage = 0;
    private var atTheEndOfList = false;

    fun loadMoreData() = viewModelScope.launch {
        if (postsLoadingLiveData.value == true) return@launch
        postsUseCase.invoke(currentPage + 1).collect {
            postsLoadingMutableLiveData.postValue(false)
            when (it) {
                is Resource.Error -> {
                    if (it.code == -20) atTheEndOfList = true
                    else postsLoadingErrorMutableLiveData.postValue(it)
                }

                Resource.Loading -> postsLoadingMutableLiveData.postValue(true)
                is Resource.Success -> {
                    val data = mutableSetOf<PostDto>()
                    data.addAll(postsMutableLiveData.value!!)
                    data.addAll(it.data)

                    postsMutableLiveData.postValue(data.toList())
                    currentPage += 1
                }
            }
        }
    }


    init {
        loadFavorites()
        loadMoreData()
    }


    private fun loadFavorites() = viewModelScope.launch {
        val favorites = getFavoriteUseCase();
        favoriteMutableLiveData.postValue(favorites)
    }

    fun togglePost(postId: Int) = viewModelScope.launch {
        toggleFavoriteUseCase(postId)
        loadFavorites()
    }

}

