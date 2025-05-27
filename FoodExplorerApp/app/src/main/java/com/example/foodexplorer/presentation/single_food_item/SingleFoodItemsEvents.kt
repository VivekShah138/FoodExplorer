package com.example.foodexplorer.presentation.single_food_item

sealed class SingleFoodItemsEvents{
    data class UpdateFavState(val itemId: Int,val isFav: Boolean): SingleFoodItemsEvents()
    data class ChangeToggleState(val state: Boolean): SingleFoodItemsEvents()
}
