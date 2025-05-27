package com.example.foodexplorer.data.repository

import android.util.Log
import com.example.foodexplorer.data.data_source.remote.FoodItemsApi
import com.example.foodexplorer.data.data_source.remote.Resource
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.repository.FoodItemsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class FoodItemsRemoteRepositoryImpl(
    private val foodItemsApi: FoodItemsApi
): FoodItemsRemoteRepository{
    override suspend fun getFoodItems(): Resource<List<FoodItem>>  = withContext(Dispatchers.IO) {
        try {
            val response = foodItemsApi.getFoodItems()
            Log.d("FoodItemsApi","Items : $response")
            Resource.Success(response)
        }catch (e: IOException) {
            Log.d("FoodItemsApi", "Network error: ${e.localizedMessage}")
            Resource.Error("Please check your internet connection.")
        }  catch (e: Exception) {
            Log.d("FoodItemsApi", "Cannot Retrieve Food Items ${e.localizedMessage}")
            Resource.Error("An unexpected error occurred")
        }
    }
}