package com.example.foodexplorer.domain.usecases

import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository

class InsertFoodItem(
    private val foodItemLocalRepository: FoodItemLocalRepository
) {

    suspend operator fun invoke(foodItem: FoodItem){
        foodItemLocalRepository.insertFoodItem(foodItem = foodItem)
    }

}