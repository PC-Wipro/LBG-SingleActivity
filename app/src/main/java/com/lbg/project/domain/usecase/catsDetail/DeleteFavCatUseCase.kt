package com.lbg.project.domain.usecase.catsDetail

import com.lbg.project.data.network.NetworkResult
import com.lbg.project.domain.mappers.CallSuccessModel
import kotlinx.coroutines.flow.Flow

interface DeleteFavCatUseCase {
    suspend fun execute(imageId: String, favId: Int): Flow<NetworkResult<CallSuccessModel>>
}