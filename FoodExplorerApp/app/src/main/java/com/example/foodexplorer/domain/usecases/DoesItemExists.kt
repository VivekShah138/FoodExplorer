package com.example.foodexplorer.domain.usecases

import com.example.foodexplorer.domain.repository.FoodItemLocalRepository

class DoesItemExists(
    private val foodItemLocalRepository: FoodItemLocalRepository
) {

    suspend operator fun invoke(itemId: Int): Boolean{
        return foodItemLocalRepository.doesItemExists(itemId = itemId)
    }

}