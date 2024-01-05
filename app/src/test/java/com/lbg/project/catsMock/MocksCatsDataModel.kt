package com.lbg.project.catsMock

import com.lbg.project.data.models.catData.CatResponse
import com.lbg.project.domain.mappers.CatDataModel
import retrofit2.Response

data class MocksCatsDataModel(
    val height: Int = 250,
    val id: String = "IDCat1",
    val url: String = "https://google.com/",
    val width: Int = 250
)

fun toResponseApiCats(mocksCatsDataModel: MocksCatsDataModel): Response<List<CatResponse>> {
    return Response.success(
        listOf(
            CatResponse(
                mocksCatsDataModel.height,
                mocksCatsDataModel.id,
                mocksCatsDataModel.url,
                mocksCatsDataModel.width
            )
        )
    )
}
fun toResponseCats(mocksCatsDataModel: MocksCatsDataModel) :List<CatDataModel> {
    return listOf(
            CatDataModel(
                imageId=mocksCatsDataModel.id,
                url=mocksCatsDataModel.url
            )
        )
}