package com.lbg.project.domain.usecase.catsDetail

import kotlinx.coroutines.flow.Flow

interface CheckFavUseCase {
    suspend fun execute(imageId:String): Flow<Int?>
}