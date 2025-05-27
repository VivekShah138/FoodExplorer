package com.example.foodexplorer.domain.repository

import com.example.foodexplorer.domain.model.FoodItem
import kotlinx.coroutines.flow.Flow


interface FoodItemLocalRepository {
    suspend fun insertFoodItemsFromAssets()
    suspend fun updateFavFlag(itemId: Int, isFav: Boolean)
    suspend fun getAllFoodItems(): Flow<List<FoodItem>>
    suspend fun getAllFavFoodItems(): Flow<List<FoodItem>>
    suspend fun doesItemExists(itemId: Int): Boolean
    suspend fun getFoodItemById(itemId:Int) : FoodItem
    suspend fun insertFoodItem(foodItem: FoodItem)
}