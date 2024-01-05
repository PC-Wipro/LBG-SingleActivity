package com.lbg.project.presentation.contracts

import com.lbg.project.domain.mappers.CatDataModel

class CatContract  {
    data class State(
        val cats: List<CatDataModel> = listOf(),
        val favCatsList: List<CatDataModel> = listOf(),
        val isLoading: Boolean = false
    )
}
