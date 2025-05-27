package com.example.foodexplorer.presentation.core.utils

sealed class Screens(val routes: String){
    data object AllFoodItemsScreen: Screens("all_food_items_screen")
    data object FavFoodItemsScreen: Screens("favourite_food_items_screen")
    data object FoodItemDetailsScreen: Screens("food_item_details_screen")
}
