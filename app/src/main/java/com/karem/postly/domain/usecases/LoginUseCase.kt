package com.karem.postly.domain.usecases

import com.karem.postly.data.cached.SettingsPref
import com.karem.postly.data.cached.entity.LoggedInUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val settingsPref: SettingsPref) {

    operator fun invoke(email: String) {
        settingsPref.updateSettings { it.user = LoggedInUser(email) }
    }
}