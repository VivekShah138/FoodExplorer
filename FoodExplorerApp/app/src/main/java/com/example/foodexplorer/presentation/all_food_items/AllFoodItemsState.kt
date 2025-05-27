package com.example.foodexplorer.presentation.all_food_items

import com.example.foodexplorer.domain.model.FoodItem

data class AllFoodItemsState(
    val allFoodItemsList: List<FoodItem> = emptyList(),
    val isListLoading: Boolean = false,
    val isError: String? = null
)
