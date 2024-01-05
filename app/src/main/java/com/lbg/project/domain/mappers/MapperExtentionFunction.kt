package com.lbg.project.domain.mappers


import com.lbg.project.data.models.SuccessResponse
import com.lbg.project.data.models.catData.CatResponse
import com.lbg.project.data.models.catData.FavouriteCatsItem

//CatData Mapper function used for Cat image listData at Cats
fun CatResponse.mapCatsDataItems(): CatDataModel {
    return CatDataModel(
        imageId = this.id,
        url = this.url
    )
}

fun FavouriteCatsItem.mapFavCatsDataItems(): CatDataModel {
    return CatDataModel(
        favId = this.id,
        url = this.image.url,
        imageId = this.imageId,
    )
}
fun SuccessResponse.mapSuccessData(): CallSuccessModel {
    return CallSuccessModel(
        successMessage = this.message,
        id = this.id
    )
}



