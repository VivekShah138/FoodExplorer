package com.example.foodexplorer.presentation.single_food_item

import androidx.lifecycle.SavedStateHandle
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
class SingleFoodItemViewModel @Inject constructor(
    private val foodItemsUseCaseWrapper: FoodItemsUseCaseWrapper,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _singleFoodItemsState =  MutableStateFlow(SingleFoodItemsState())
    val singleFoodItemsState: StateFlow<SingleFoodItemsState> = _singleFoodItemsState.asStateFlow()

    init {
        savedStateHandle.get<Int>("itemId")?.let { itemId ->
            if(itemId != -1){
                viewModelScope.launch {
                    foodItemsUseCaseWrapper.getFoodItemById(itemId).also { foodItem ->
                        _singleFoodItemsState.value = singleFoodItemsState.value.copy(
                            foodItem = foodItem,
                            isFav = foodItem.isFavourite
                        )
                    }
                }
            }
        }
    }


    fun onEvent(singleFoodItemsEvents: SingleFoodItemsEvents){
        when(singleFoodItemsEvents){
            is SingleFoodItemsEvents.UpdateFavState -> {
                viewModelScope.launch{
                    foodItemsUseCaseWrapper.updateFavFlag(itemId = singleFoodItemsEvents.itemId, isFav = singleFoodItemsEvents.isFav)
                }
            }

            is SingleFoodItemsEvents.ChangeToggleState -> {
                _singleFoodItemsState.value = _singleFoodItemsState.value.copy(
                    isFav = singleFoodItemsEvents.state
                )
            }
        }
    }
}