package com.karem.postly.ui.splash

import androidx.lifecycle.ViewModel
import com.karem.postly.domain.usecases.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val isUserLoggedInUseCase: IsUserLoggedInUseCase) :
    ViewModel() {

    fun isLoggedIn() = isUserLoggedInUseCase.invoke()


}