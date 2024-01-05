package com.lbg.project.domain.usecase.cats

import com.lbg.project.data.network.NetworkResult
import com.lbg.project.domain.mappers.CatDataModel
import com.lbg.project.domain.mappers.mapCatsDataItems
import com.lbg.project.domain.repositories.CatsRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetCatsUseCaseImpl(private val catsRepo: CatsRepository): GetCatsUseCase {
    override suspend fun execute() = flow<NetworkResult<List<CatDataModel>>> {
            emit(NetworkResult.Loading())
            with(catsRepo.fetchCats()) {
                if (isSuccessful) {
                    val catDataList = this.body()?.map { cat ->
                        cat.mapCatsDataItems()
                    }
                    emit(NetworkResult.Success(catDataList))
                } else {
                    emit(NetworkResult.Error(this.errorBody().toString()))
                }
            }
        }.catch {
            emit(NetworkResult.Error(it.localizedMessage))
        }
}
