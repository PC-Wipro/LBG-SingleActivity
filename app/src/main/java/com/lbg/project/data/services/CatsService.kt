package com.lbg.project.data.services


import com.lbg.project.data.models.SuccessResponse
import com.lbg.project.data.models.catData.CatResponse
import com.lbg.project.data.models.catData.FavouriteCatsItem
import com.lbg.project.data.models.catDetails.PostFavCatModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CatsService {


    @GET("v1/images/search")
    suspend fun fetchCatsImages(
        @Query("limit") limit: Int
    ): Response<List<CatResponse>>

    @GET("v1/favourites")
    suspend fun fetchFavouriteCats(
        @Query("sub_id") subId: String
    ): Response<List<FavouriteCatsItem>>

    @POST("v1/favourites")
    suspend fun postFavouriteCat(
        @Body favCat: PostFavCatModel
    ): Response<SuccessResponse>

    @DELETE("v1/favourites/{favourite_id}")
    suspend fun deleteFavouriteCat(
        @Path("favourite_id") favouriteId: Int
    ): Response<SuccessResponse>


}