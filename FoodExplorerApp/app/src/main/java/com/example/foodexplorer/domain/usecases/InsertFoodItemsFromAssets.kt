package com.example.foodexplorer.domain.usecases

import com.example.foodexplorer.domain.repository.FoodItemLocalRepository

class InsertFoodItemsFromAssets(
    private val foodItemLocalRepository: FoodItemLocalRepository
) {

    suspend operator fun invoke(){
        foodItemLocalRepository.insertFoodItemsFromAssets()
    }

}