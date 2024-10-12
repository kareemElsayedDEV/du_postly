package com.karem.postly

import androidx.lifecycle.ViewModel
import com.karem.postly.domain.usecases.IsUserLoggedInUseCase
import com.karem.postly.domain.usecases.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val logoutUseCase: LogoutUseCase,
    val isUserLoggedInUseCase: IsUserLoggedInUseCase
) : ViewModel() {

}