package com.example.foodexplorer.domain.repository

import com.example.foodexplorer.data.data_source.remote.Resource
import com.example.foodexplorer.domain.model.FoodItem

interface FoodItemsRemoteRepository {

    suspend fun getFoodItems(): Resource<List<FoodItem>>

}