package com.lbg.project.domain.usecase.catsDetail

import com.lbg.project.data.network.NetworkResult
import com.lbg.project.data.models.catDetails.PostFavCatModel
import com.lbg.project.domain.mappers.CallSuccessModel
import com.lbg.project.domain.mappers.mapSuccessData
import com.lbg.project.domain.repositories.CatDetailsRepository
import com.lbg.project.utils.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostFavCatUseCaseImpl(private val catDetailsRepo: CatDetailsRepository) : PostFavCatUseCase {
    override suspend fun execute(imageId: String) = flow<NetworkResult<CallSuccessModel>> {
        emit(NetworkResult.Loading())
        val favCat = PostFavCatModel(imageId, Constants.SUB_ID)
        with(catDetailsRepo.postFavouriteCat(favCat)) {
            if (isSuccessful) {
                emit(NetworkResult.Success(this.body()?.mapSuccessData()))
                this.body()?.id?.let { catDetailsRepo.insertFavouriteCat(it, favCat.imageId) }
            } else {
                emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }
}
