package com.example.foodexplorer.data.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FoodItemEntity(
    val itemName: String,
    @PrimaryKey
    val itemId: Int,
    val itemDescription: String,
    val itemRating: Double,
    val itemImageUrl: String,
    val itemPrice: Double,
    val isFavourite: Boolean
)
