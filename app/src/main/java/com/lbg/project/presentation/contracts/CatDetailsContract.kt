package com.lbg.project.presentation.contracts


import com.lbg.project.domain.mappers.CallSuccessModel


class CatDetailsContract {
    data class State(
        val postFavCatSuccess: CallSuccessModel?,
        val deleteFavCatSuccess: CallSuccessModel?
    )

}