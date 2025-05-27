package com.example.foodexplorer.data.repository

import android.util.Log
import com.example.foodexplorer.data.data_source.remote.FoodItemsApi
import com.example.foodexplorer.data.data_source.remote.Resource
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.repository.FoodItemsRemoteRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class FoodItemsRemoteRepositoryImplTest {

    private lateinit var repository: FoodItemsRemoteRepository
    private lateinit var foodItemsApi: FoodItemsApi

    @Before
    fun setUp() {
        foodItemsApi  = mockk<FoodItemsApi>(relaxed = true)
        repository = FoodItemsRemoteRepositoryImpl(foodItemsApi)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }


    @Test
    fun `get food items from api returns success with list of food items`() = runTest {
        val mockItems = listOf(
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
            )
        )

        coEvery { foodItemsApi.getFoodItems() } returns mockItems

        val response = repository.getFoodItems()

        coVerify(exactly = 1) { foodItemsApi.getFoodItems() }

        Truth.assertThat(response).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat((response as Resource.Success).data).containsExactlyElementsIn(mockItems)
    }

    @Test
    fun `get food items from api returns error on io exception`() = runTest {
        coEvery { foodItemsApi.getFoodItems() } throws IOException("No internet")

        val response = repository.getFoodItems()

        coVerify(exactly = 1) { foodItemsApi.getFoodItems() }

        Truth.assertThat(response).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((response as Resource.Error).message).isEqualTo("Please check your internet connection.")
    }

    @Test
    fun `get food items from api returns error on exception`() = runTest {
        coEvery { foodItemsApi.getFoodItems() } throws Exception()

        val response = repository.getFoodItems()

        coVerify(exactly = 1) { foodItemsApi.getFoodItems() }

        Truth.assertThat(response).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((response as Resource.Error).message).isEqualTo("An unexpected error occurred.")
    }



}