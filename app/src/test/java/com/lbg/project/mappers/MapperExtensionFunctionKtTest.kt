package com.lbg.project.mappers


import com.lbg.project.catsMock.MockFavouriteCatsResponse
import com.lbg.project.catsMock.MockSuccessResponse
import com.lbg.project.data.models.SuccessResponse
import com.lbg.project.data.models.catData.CatResponse
import com.lbg.project.data.models.catData.FavouriteCatsItem
import com.lbg.project.domain.mappers.mapCatsDataItems
import com.lbg.project.domain.mappers.mapFavCatsDataItems
import com.lbg.project.domain.mappers.mapSuccessData
import org.junit.Test
import kotlin.test.assertEquals

class MapperExtensionFunctionKtTest {

    @Test
    fun mapCatsDataItems() {
        val catResponse = CatResponse(23, "img1", "www.image.com", 200)
        val finalResult = catResponse.mapCatsDataItems()
        assertEquals(catResponse.id, finalResult.imageId)
    }

    @Test
    fun mapFavCatsDataItems() {
        val mocksFavCatsDataModel = MockFavouriteCatsResponse()
        val favCatResponse = FavouriteCatsItem(
            mocksFavCatsDataModel.createdAt,
            mocksFavCatsDataModel.id,
            mocksFavCatsDataModel.image,
            mocksFavCatsDataModel.imageId,
            mocksFavCatsDataModel.subId,
            mocksFavCatsDataModel.userId
        )
        val finalResult = favCatResponse.mapFavCatsDataItems()
        assertEquals(favCatResponse.imageId, finalResult.imageId)
        assertEquals(favCatResponse.image.url, finalResult.url)
    }

    @Test
    fun mapSuccessData() {
        val mockSuccessResponse = MockSuccessResponse()
        val successResponse = SuccessResponse(mockSuccessResponse.id, mockSuccessResponse.message)
        val finalResult = successResponse.mapSuccessData()
        assertEquals(successResponse.id, finalResult.id)
        assertEquals(successResponse.message, finalResult.successMessage)

    }
}