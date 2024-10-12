package com.karem.postly.domain.usecases

import com.karem.postly.data.cached.SettingsPref
import com.karem.postly.data.local.daos.FavoritePostDao
import com.karem.postly.data.local.entities.Favorite
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val favoritePostDao: FavoritePostDao,
    private val settingsPref: SettingsPref
) {
    suspend operator fun invoke(): List<Int> {
        val userEmail = settingsPref.getSettings().user!!.email;
        return favoritePostDao.getAllFavorite(userEmail).map { it.postId }
    }
}