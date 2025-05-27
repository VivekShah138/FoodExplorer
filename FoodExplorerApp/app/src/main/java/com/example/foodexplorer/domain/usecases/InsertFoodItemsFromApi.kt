package com.example.foodexplorer.domain.usecases

import com.example.foodexplorer.data.data_source.remote.Resource
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository
import com.example.foodexplorer.domain.repository.FoodItemsRemoteRepository

class InsertFoodItemsFromApi(
    private val foodItemLocalRepository: FoodItemLocalRepository,
    private val foodItemsRemoteRepository: FoodItemsRemoteRepository
) {

    suspend operator fun invoke(): Resource<Unit>{
        when(val response = foodItemsRemoteRepository.getFoodItems()){
            is Resource.Error -> {
                return Resource.Error(response.message)
            }
            is Resource.Success -> {
                response.data.forEach { foodItem ->
                    if(!foodItemLocalRepository.doesItemExists(itemId = foodItem.id)){
                        foodItemLocalRepository.insertFoodItem(foodItem = foodItem)
                    }
                }
                return Resource.Success(Unit)
            }
        }
    }
}