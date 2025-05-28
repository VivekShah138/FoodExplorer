package com.example.foodexplorer.presentation.all_food_items

sealed class AllFoodItemsEvents{
    data object LoadAllFoodItemsFromDb: AllFoodItemsEvents()
    data object LoadAllFoodItemsFromApi: AllFoodItemsEvents()
    data class UpdateFavState(val itemId: Int,val isFav: Boolean): AllFoodItemsEvents()
}
