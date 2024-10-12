package com.karem.postly.domain.usecases

import com.karem.postly.data.cached.SettingsPref
import com.karem.postly.data.local.daos.FavoritePostDao
import com.karem.postly.data.local.entities.Favorite
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val favoritePostDao: FavoritePostDao,
    private val settingsPref: SettingsPref
) {

    suspend operator fun invoke(postId: Int) {
        val userEmail = settingsPref.getSettings().user!!.email;
        if (favoritePostDao.getFavoriteById(postId) != null)
            favoritePostDao.deleteFavorite(Favorite(postId, userEmail))
        else
            favoritePostDao.insert(Favorite(postId, userEmail))
    }
}