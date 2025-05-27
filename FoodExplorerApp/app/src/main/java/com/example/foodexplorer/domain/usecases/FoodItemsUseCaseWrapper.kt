package com.example.foodexplorer.domain.usecases

data class FoodItemsUseCaseWrapper(
    val doesItemExists: DoesItemExists,
    val getFoodItemById: GetFoodItemById,
    val getAllFoodItems: GetAllFoodItems,
    val getAllFavFoodItems: GetAllFavFoodItems,
    val insertFoodItemsFromApi: InsertFoodItemsFromApi,
    val insertFoodItem: InsertFoodItem,
    val insertFoodItemsFromAssets: InsertFoodItemsFromAssets,
    val updateFavFlag: UpdateFavFlag
)
