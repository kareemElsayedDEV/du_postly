package com.karem.postly.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karem.postly.core.Resource
import com.karem.postly.domain.models.PostDto
import com.karem.postly.domain.usecases.FavoritePostsUseCase
import com.karem.postly.domain.usecases.GetFavoriteUseCase
import com.karem.postly.domain.usecases.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoritePostsUseCase: FavoritePostsUseCase,
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

    private fun loadFavoritePosts() = viewModelScope.launch {
        currentPage += 1;
        loadFavorites()
        favoritePostsUseCase.invoke().collect {
            postsLoadingMutableLiveData.postValue(false)
            when (it) {
                is Resource.Error -> {
                    if (it.code == -20) atTheEndOfList = true
                    else postsLoadingErrorMutableLiveData.postValue(it)
                }

                Resource.Loading -> postsLoadingMutableLiveData.postValue(true)
                is Resource.Success -> {
                    postsMutableLiveData.postValue(it.data)
                }
            }
        }
    }


    init {
        loadFavoritePosts()
    }


    private suspend fun loadFavorites() {
        val favorites = getFavoriteUseCase();
        favoriteMutableLiveData.postValue(favorites)
    }

    fun togglePost(postId: Int) = viewModelScope.launch {
        toggleFavoriteUseCase(postId)
        loadFavorites()
    }
}