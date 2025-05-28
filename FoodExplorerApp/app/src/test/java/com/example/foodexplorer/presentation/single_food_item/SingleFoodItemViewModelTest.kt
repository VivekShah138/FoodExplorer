package com.example.foodexplorer.presentation.single_food_item

import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.SavedStateHandle
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.usecases.FoodItemsUseCaseWrapper
import com.example.foodexplorer.presentation.all_food_items.AllFoodItemsViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SingleFoodItemViewModelTest {

    private lateinit var viewModel: AllFoodItemsViewModel
    private lateinit var foodItemsUseCaseWrapper: FoodItemsUseCaseWrapper
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var savedStateHandle: SavedStateHandle


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        foodItemsUseCaseWrapper = mockk<FoodItemsUseCaseWrapper>(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load item data when item id is valid`() {
        coEvery { foodItemsUseCaseWrapper.getFoodItemById(itemId = 1) } returns getResults()
        savedStateHandle = SavedStateHandle(mapOf("itemId" to 1))
        val viewModel = SingleFoodItemViewModel(foodItemsUseCaseWrapper = foodItemsUseCaseWrapper,savedStateHandle = savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { foodItemsUseCaseWrapper.getFoodItemById(itemId = 1) }
        Truth.assertThat(viewModel.singleFoodItemsState.value.foodItem).isEqualTo(getResults())
    }

    @Test
    fun `do not load item data when item id is invalid`() {
        savedStateHandle = SavedStateHandle(mapOf("itemId" to -1))
        val viewModel = SingleFoodItemViewModel(foodItemsUseCaseWrapper = foodItemsUseCaseWrapper,savedStateHandle = savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 0) { foodItemsUseCaseWrapper.getAllFoodItems() }
        Truth.assertThat(viewModel.singleFoodItemsState.value.foodItem).isEqualTo(null)
    }


    private fun getResults(): FoodItem {
        return FoodItem(
            id = 1,
            name = "Pizza",
            description = "Description",
            rating = 4.5,
            price = 5.05,
            isFavourite = false,
            imageUrl = ""
        )
    }
}