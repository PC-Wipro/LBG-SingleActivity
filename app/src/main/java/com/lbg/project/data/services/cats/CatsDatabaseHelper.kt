package com.lbg.project.data.services.cats

import com.lbg.project.data.models.catData.FavouriteCatsItem

interface CatsDatabaseHelper {
    suspend fun insertFavCatImageRelation(favCatItems: List<FavouriteCatsItem>): List<Long>
}