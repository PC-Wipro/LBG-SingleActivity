package com.lbg.project.catsMock

import com.google.gson.annotations.SerializedName
import com.lbg.project.data.models.catDetails.PostFavCatModel
import com.lbg.project.utils.Constants

data class MockPostFavCatModel(
    @SerializedName("image_id")
    val imageId: String ="mi5",
    @SerializedName("sub_id")
    val subId: String= Constants.SUB_ID
)
fun toRequestPostFavCatData(mockPostFavCatModel: MockPostFavCatModel): PostFavCatModel {
    return PostFavCatModel(mockPostFavCatModel.imageId,mockPostFavCatModel.subId)
}