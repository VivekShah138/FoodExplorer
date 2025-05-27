package com.example.foodexplorer.domain.usecases

import com.example.foodexplorer.domain.repository.FoodItemLocalRepository

class UpdateFavFlag(
    private val foodItemLocalRepository: FoodItemLocalRepository
) {

    suspend operator fun invoke(itemId: Int, isFav: Boolean){
        return foodItemLocalRepository.updateFavFlag(itemId = itemId,isFav = isFav)
    }

}