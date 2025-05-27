package com.example.foodexplorer.presentation.single_food_item

import com.example.foodexplorer.domain.model.FoodItem

data class SingleFoodItemsState(
    val foodItem: FoodItem? = null,
    val isListLoading: Boolean = false,
    val isFav: Boolean = false
)
