package com.lbg.project.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_details")
data class FavImageEntity(
    val favouriteId: Int,
    @PrimaryKey
    val imageId: String
)
