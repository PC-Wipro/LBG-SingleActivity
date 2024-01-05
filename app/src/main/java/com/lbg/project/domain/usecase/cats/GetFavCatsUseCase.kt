package com.lbg.project.domain.usecase.cats

import com.lbg.project.data.network.NetworkResult
import com.lbg.project.domain.mappers.CatDataModel
import kotlinx.coroutines.flow.Flow

interface GetFavCatsUseCase {
    suspend fun execute(): Flow<NetworkResult<List<CatDataModel>>>
}