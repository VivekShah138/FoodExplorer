package com.example.foodexplorer.domain.model


data class FoodItem(
    val name: String,
    val id: Int,
    val description: String,
    val rating: Double,
    val imageUrl: String,
    val price: Double,
    val isFavourite: Boolean = false
)
