package com.example.foodexplorer.data.data_source.remote

import com.example.foodexplorer.domain.model.FoodItem
import retrofit2.http.GET

interface FoodItemsApi {

    @GET("foodexplorer/FoodItems")
    suspend fun getFoodItems(): List<FoodItem>
}