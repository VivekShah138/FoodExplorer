package com.example.foodexplorer.presentation.all_food_items

import com.example.foodexplorer.data.data_source.remote.Resource
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.usecases.FoodItemsUseCaseWrapper
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AllFoodItemsViewModelTest {

    private lateinit var viewModel: AllFoodItemsViewModel
    private lateinit var foodItemsUseCaseWrapper: FoodItemsUseCaseWrapper
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        foodItemsUseCaseWrapper = mockk<FoodItemsUseCaseWrapper>(relaxed = true)
        viewModel = AllFoodItemsViewModel(foodItemsUseCaseWrapper)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `load initial items from database where database is not null`()  = runTest{

        coEvery { foodItemsUseCaseWrapper.getAllFoodItems() } returns flowOf(getResults())

        viewModel = AllFoodItemsViewModel(foodItemsUseCaseWrapper)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 2) { foodItemsUseCaseWrapper.getAllFoodItems() }

        Truth.assertThat(viewModel.allFoodItemsState.value.allFoodItemsList).isEqualTo(getResults())
    }

    @Test
    fun `load initial items from database where database is null but api is success`()  = runTest{

        coEvery { foodItemsUseCaseWrapper.getAllFoodItems() } returnsMany listOf(flowOf(emptyList()),flowOf(getResults()))
        coEvery { foodItemsUseCaseWrapper.insertFoodItemsFromApi() } returns Resource.Success(Unit)

        viewModel = AllFoodItemsViewModel(foodItemsUseCaseWrapper)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 3) { foodItemsUseCaseWrapper.getAllFoodItems() }
        coVerify(exactly = 1) { foodItemsUseCaseWrapper.insertFoodItemsFromApi() }

        Truth.assertThat(viewModel.allFoodItemsState.value.allFoodItemsList).isEqualTo(getResults())
    }

    @Test
    fun `load initial items from database where database is null but api is failure and show error`()  = runTest{
        coEvery { foodItemsUseCaseWrapper.getAllFoodItems() } returnsMany listOf(flowOf(emptyList()),flowOf(emptyList()))
        coEvery { foodItemsUseCaseWrapper.insertFoodItemsFromApi() } returns Resource.Error("No internet connection.")

        viewModel = AllFoodItemsViewModel(foodItemsUseCaseWrapper)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 3) { foodItemsUseCaseWrapper.getAllFoodItems() }
        coVerify(exactly = 1) { foodItemsUseCaseWrapper.insertFoodItemsFromApi() }

        Truth.assertThat(viewModel.allFoodItemsState.value.isError).isEqualTo("No internet connection.")

    }

    @Test
    fun `load items from api where api is failure but displays already existing items`()  = runTest{
        coEvery { foodItemsUseCaseWrapper.getAllFoodItems() } returns flowOf(getResults())
        coEvery { foodItemsUseCaseWrapper.insertFoodItemsFromApi() } returns Resource.Error("No internet connection.")


        viewModel.onEvent(AllFoodItemsEvents.LoadAllFoodItemsFromApi)

        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 2) { foodItemsUseCaseWrapper.getAllFoodItems() }
        coVerify(exactly = 1) { foodItemsUseCaseWrapper.insertFoodItemsFromApi() }

        Truth.assertThat(viewModel.allFoodItemsState.value.isError).isEqualTo(null)
        Truth.assertThat(viewModel.allFoodItemsState.value.allFoodItemsList).isEqualTo(getResults())

    }

    @Test
    fun `load items from asset folder`()  = runTest{
        coEvery { foodItemsUseCaseWrapper.getAllFoodItems() } returns flowOf(getResults())

        viewModel.onEvent(AllFoodItemsEvents.LoadAllFoodItemsFromDb)

        testDispatcher.scheduler.advanceUntilIdle()


        coVerify(exactly = 2) { foodItemsUseCaseWrapper.getAllFoodItems() }
        coVerify(exactly = 1) { foodItemsUseCaseWrapper.insertFoodItemsFromAssets() }


        Truth.assertThat(viewModel.allFoodItemsState.value.isError).isEqualTo(null)
        Truth.assertThat(viewModel.allFoodItemsState.value.allFoodItemsList).isEqualTo(getResults())
    }

    @Test
    fun `update items favourite state`()  = runTest{

        viewModel.onEvent(AllFoodItemsEvents.UpdateFavState(itemId = 1, isFav = true))
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { foodItemsUseCaseWrapper.updateFavFlag(itemId = 1, isFav = true)}

    }


    private fun getResults() :List<FoodItem>{
        return listOf(
            FoodItem(
                id = 1,
                name = "Pizza",
                description = "Description",
                rating = 4.5,
                price = 5.05,
                isFavourite = false,
                imageUrl = ""
            ),
            FoodItem(
                id = 2,
                name = "Pizza",
                description = "Description",
                rating = 4.5,
                price = 5.05,
                isFavourite = false,
                imageUrl = ""
            ),
            FoodItem(
                id = 3,
                name = "Pizza",
                description = "Description",
                rating = 4.5,
                price = 5.05,
                isFavourite = false,
                imageUrl = ""
            )
        )
    }
}