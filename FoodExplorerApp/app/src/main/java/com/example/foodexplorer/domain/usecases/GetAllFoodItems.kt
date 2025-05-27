package com.example.foodexplorer.domain.usecases

import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository
import kotlinx.coroutines.flow.Flow

class GetAllFoodItems(
    private val foodItemLocalRepository: FoodItemLocalRepository
) {

    suspend operator fun invoke(): Flow<List<FoodItem>> {
        return foodItemLocalRepository.getAllFoodItems()
    }

}