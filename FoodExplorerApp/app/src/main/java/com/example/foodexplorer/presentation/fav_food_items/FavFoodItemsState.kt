package com.example.foodexplorer.presentation.fav_food_items

import com.example.foodexplorer.domain.model.FoodItem

data class FavFoodItemsState(
    val favFoodItemsList: List<FoodItem> = emptyList(),
    val isListLoading: Boolean = false,
)
