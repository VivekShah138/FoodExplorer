package com.example.foodexplorer.presentation.fav_food_items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodexplorer.domain.usecases.FoodItemsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavFoodItemsViewModel @Inject constructor(
    private val foodItemsUseCaseWrapper: FoodItemsUseCaseWrapper

): ViewModel() {

    private val _favFoodItemsState =  MutableStateFlow(FavFoodItemsState())
    val favFoodItemsState: StateFlow<FavFoodItemsState> = _favFoodItemsState.asStateFlow()

    init {

        viewModelScope.launch {
            foodItemsUseCaseWrapper.getAllFavFoodItems().collect { itemList ->
                _favFoodItemsState.value = _favFoodItemsState.value.copy(
                    favFoodItemsList = itemList
                )
            }
        }
    }


    fun onEvent(favFoodItemsEvents: FavFoodItemsEvents){
        when(favFoodItemsEvents){
            is FavFoodItemsEvents.UpdateFavState -> {
                viewModelScope.launch{
                    foodItemsUseCaseWrapper.updateFavFlag(itemId = favFoodItemsEvents.itemId, isFav = favFoodItemsEvents.isFav)
                }
            }
        }
    }
}