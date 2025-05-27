package com.example.foodexplorer.domain.usecases

import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository

class GetFoodItemById(
    private val foodItemLocalRepository: FoodItemLocalRepository
) {

    suspend operator fun invoke(itemId: Int): FoodItem{
        return foodItemLocalRepository.getFoodItemById(itemId = itemId)
    }

}