package com.karem.postly.ui.login

import androidx.lifecycle.ViewModel
import com.karem.postly.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val loginUseCase: LoginUseCase) : ViewModel() {
    fun login(email: String) {
        loginUseCase.invoke(email)
    }
}