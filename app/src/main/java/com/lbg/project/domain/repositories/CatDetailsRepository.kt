package com.lbg.project.domain.repositories


import com.lbg.project.data.models.SuccessResponse
import com.lbg.project.data.models.catDetails.PostFavCatModel
import retrofit2.Response

interface CatDetailsRepository {
    suspend fun postFavouriteCat(favCat: PostFavCatModel): Response<SuccessResponse>
    suspend fun insertFavouriteCat(favCatId: Int, catImgId: String): Long
    suspend fun deleteFavouriteCatApi(favouriteId: Int): Response<SuccessResponse>
    suspend fun deleteFavouriteCatLocal(imgId: String): Int

    suspend fun fetchIsFavouriteRelation(imageId: String): Int?

}