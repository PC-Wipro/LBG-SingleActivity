package com.lbg.project.domain.usecase.cats

import com.lbg.project.data.network.NetworkResult
import com.lbg.project.domain.mappers.CatDataModel
import com.lbg.project.domain.mappers.mapFavCatsDataItems
import com.lbg.project.domain.repositories.CatsRepository
import com.lbg.project.utils.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetFavCatsUseCaseImpl(private val catRepo: CatsRepository) : GetFavCatsUseCase {
    override suspend fun execute() = flow<NetworkResult<List<CatDataModel>>> {
        emit(NetworkResult.Loading())
        with(catRepo.fetchFavouriteCats(Constants.SUB_ID)) {
            if (isSuccessful) {
                val catDataList = this.body()?.map { cat ->
                    cat.mapFavCatsDataItems()
                }
                emit(NetworkResult.Success(catDataList?.reversed()))
                this.body()?.let { catRepo.insertFavouriteCats(it) }
            } else {
                emit(NetworkResult.Error(this.errorBody().toString()))
            }
        }
    }.catch {
        emit(NetworkResult.Error(it.localizedMessage))
    }
}
