package com.lbg.project.data.services.catsDetail

import com.lbg.project.data.models.SuccessResponse
import com.lbg.project.data.models.catDetails.PostFavCatModel
import retrofit2.Response

interface CatDetailsApiServiceHelper {
    suspend fun postFavouriteCat(favCat: PostFavCatModel): Response<SuccessResponse>
    suspend fun deleteFavouriteCat(favouriteId: Int): Response<SuccessResponse>

}