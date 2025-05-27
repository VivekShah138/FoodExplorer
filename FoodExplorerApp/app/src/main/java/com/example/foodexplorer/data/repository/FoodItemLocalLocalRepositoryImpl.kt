package com.example.foodexplorer.data.repository

import android.content.Context
import com.example.foodexplorer.data.data_source.local.FoodItemDao
import com.example.foodexplorer.data.utils.FoodItemJsonParser
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository
import com.example.foodexplorer.data.utils.toEntity
import com.example.foodexplorer.data.utils.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FoodItemLocalLocalRepositoryImpl @Inject constructor(
    private val context: Context,
    private val foodItemDao: FoodItemDao
):FoodItemLocalRepository {
    override suspend fun insertFoodItemsFromAssets() = withContext(Dispatchers.IO){

        val jsonString = context.assets.open("food_items.json").bufferedReader().use { foodItems ->
            foodItems.readText()
        }
        val foodItemList = FoodItemJsonParser.parseJsonToFoodItems(jsonString)
        foodItemList.forEach { foodItem ->

            if(!foodItemDao.doesItemExists(foodItem.id)){
                foodItemDao.insertItem(foodItem.toEntity())
            }
        }
    }

    override suspend fun updateFavFlag(itemId: Int, isFav: Boolean) = withContext(Dispatchers.IO){
        foodItemDao.updateFavoriteItem(id = itemId,isFav = isFav)
    }

    override suspend fun getAllFoodItems(): Flow<List<FoodItem>> {
        return foodItemDao.getAllItems().map {foodItems ->
            foodItems.map { foodItem ->
                foodItem.toModel()
            }
        }
    }

    override suspend fun getAllFavFoodItems(): Flow<List<FoodItem>> {
        return foodItemDao.getAllFavouriteItems().map {foodItems ->
            foodItems.map { foodItem ->
                foodItem.toModel()
            }
        }
    }

    override suspend fun doesItemExists(itemId: Int): Boolean = withContext(Dispatchers.IO) {
        foodItemDao.doesItemExists(itemId = itemId)
    }

    override suspend fun getFoodItemById(itemId: Int): FoodItem = withContext(Dispatchers.IO) {
        foodItemDao.getFoodItemById(itemId = itemId).toModel()
    }

    override suspend fun insertFoodItem(foodItem: FoodItem) = withContext(Dispatchers.IO) {
        foodItemDao.insertItem(items = foodItem.toEntity())
    }
}