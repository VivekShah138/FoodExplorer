package com.example.foodexplorer.presentation.all_food_items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodexplorer.data.data_source.remote.Resource
import com.example.foodexplorer.domain.usecases.FoodItemsUseCaseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFoodItemsViewModel @Inject constructor(
    private val foodItemsUseCaseWrapper: FoodItemsUseCaseWrapper

): ViewModel() {

    private val _allFoodItemsState =  MutableStateFlow(AllFoodItemsState())
    val allFoodItemsState: StateFlow<AllFoodItemsState> = _allFoodItemsState.asStateFlow()

    init {
        loadFromLocalDb()
    }


    fun onEvent(allFoodItemsEvents: AllFoodItemsEvents){
        when(allFoodItemsEvents){
            AllFoodItemsEvents.LoadAllFoodItemsFromDb -> {
                _allFoodItemsState.value = allFoodItemsState.value.copy(
                    isListLoading = true
                )
                viewModelScope.launch{

                    foodItemsUseCaseWrapper.insertFoodItemsFromAssets()
                    foodItemsUseCaseWrapper.getAllFoodItems().collect{itemList->
                        _allFoodItemsState.value = allFoodItemsState.value.copy(
                            allFoodItemsList = itemList,
                            isError = null,
                            isListLoading = false
                        )
                    }
                }
            }
            is AllFoodItemsEvents.UpdateFavState -> {
                viewModelScope.launch{
                    foodItemsUseCaseWrapper.updateFavFlag(itemId = allFoodItemsEvents.itemId, isFav = allFoodItemsEvents.isFav)
                }
            }

            is AllFoodItemsEvents.LoadAllFoodItemsFromApi -> {
                fetchFoodItemsFromApi()
            }
        }
    }

    private fun fetchFoodItemsFromApi() {
        viewModelScope.launch {
            _allFoodItemsState.value = _allFoodItemsState.value.copy(isListLoading = true)

            when (val result = foodItemsUseCaseWrapper.insertFoodItemsFromApi()) {
                is Resource.Success -> {
                    foodItemsUseCaseWrapper.getAllFoodItems().collect{ foodItemsList ->
                        _allFoodItemsState.value = _allFoodItemsState.value.copy(
                            isListLoading = false,
                            allFoodItemsList = foodItemsList
                        )
                    }
                }
                is Resource.Error -> {
                    val databaseResponse = foodItemsUseCaseWrapper.getAllFoodItems().first()
                    if(databaseResponse.isEmpty()){
                        _allFoodItemsState.value = _allFoodItemsState.value.copy(
                            isListLoading = false,
                            isError = result.message
                        )
                    }
                    else{
                        _allFoodItemsState.value = _allFoodItemsState.value.copy(
                            isListLoading = false,
                            allFoodItemsList = databaseResponse
                        )
                    }
                }
            }
        }
    }

    private fun loadFromLocalDb(){
        _allFoodItemsState.value = allFoodItemsState.value.copy(
            isListLoading = true
        )
        viewModelScope.launch{
            foodItemsUseCaseWrapper.getAllFoodItems().collect{itemList->

                if(itemList.isEmpty()){
                    fetchFoodItemsFromApi()
                }else{
                    _allFoodItemsState.value = allFoodItemsState.value.copy(
                        allFoodItemsList = itemList,
                        isError = null,
                        isListLoading = false
                    )
                }
            }
        }
    }

}