package com.example.foodexplorer.data.data_source.remote

import com.example.foodexplorer.domain.model.FoodItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodItemsApi {

    @GET("foodexplorer/FoodItems")
    suspend fun getFoodItems(): List<FoodItem>

    @GET("foodexplorer/FoodItems")
    suspend fun getSearchedFoodItems(@Query("name")itemName: String): List<FoodItem>
}