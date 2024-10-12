package com.karem.postly.domain.usecases

import com.karem.postly.data.cached.SettingsPref
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val settingsPref: SettingsPref) {

    operator fun invoke() = settingsPref.updateSettings { it.user = null }

}