package com.example.foodexplorer.presentation.fav_food_items

sealed class FavFoodItemsEvents{
    data class UpdateFavState(val itemId: Int,val isFav: Boolean): FavFoodItemsEvents()
}
