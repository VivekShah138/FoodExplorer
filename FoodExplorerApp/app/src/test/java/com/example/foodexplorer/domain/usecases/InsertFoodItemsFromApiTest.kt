package com.example.foodexplorer.domain.usecases

import android.content.Context
import com.example.foodexplorer.data.data_source.local.FoodItemDao
import com.example.foodexplorer.data.data_source.remote.Resource
import com.example.foodexplorer.data.repository.FoodItemLocalLocalRepositoryImpl
import com.example.foodexplorer.data.utils.toEntity
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository
import com.example.foodexplorer.domain.repository.FoodItemsRemoteRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.core.ValueClassSupport.boxedValue
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class InsertFoodItemsFromApiTest{

    private lateinit var localRepository: FoodItemLocalRepository
    private lateinit var remoteRepository: FoodItemsRemoteRepository
    private lateinit var insertFoodItemsFromApi: InsertFoodItemsFromApi

    @Before
    fun setUp() {
        localRepository = mockk<FoodItemLocalRepository>(relaxed = true)
        remoteRepository = mockk<FoodItemsRemoteRepository>(relaxed = true)
        insertFoodItemsFromApi = InsertFoodItemsFromApi(foodItemLocalRepository = localRepository, foodItemsRemoteRepository = remoteRepository )
    }

    @Test
    fun `insert food items from api which does not exists returns success`() = runTest {
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

        coEvery { remoteRepository.getFoodItems() } returns Resource.Success(mockItems)
        coEvery { localRepository.doesItemExists(itemId = 1) } returns false
        coEvery { localRepository.doesItemExists(itemId = 2) } returns false
        coEvery { localRepository.doesItemExists(itemId = 3) } returns false

        val response = insertFoodItemsFromApi()

        coVerify(exactly = 1) { remoteRepository.getFoodItems()  }
        coVerify(exactly = 1) { localRepository.doesItemExists(1) }
        coVerify(exactly = 1) { localRepository.doesItemExists(2) }
        coVerify(exactly = 1) { localRepository.doesItemExists(3) }
        coVerify(exactly = 1) { localRepository.insertFoodItem(match { it.id == 1 }) }
        coVerify(exactly = 1) { localRepository.insertFoodItem(match { it.id == 2 }) }
        coVerify(exactly = 1) { localRepository.insertFoodItem(match { it.id == 3 }) }

        Truth.assertThat(response).isInstanceOf(Resource.Success(Unit)::class.java)
    }

    @Test
    fun `does not insert food items from api which does exists returns success`() = runTest {
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

        coEvery { remoteRepository.getFoodItems() } returns Resource.Success(mockItems)
        coEvery { localRepository.doesItemExists(itemId = 1) } returns true
        coEvery { localRepository.doesItemExists(itemId = 2) } returns true
        coEvery { localRepository.doesItemExists(itemId = 3) } returns true

        val response = insertFoodItemsFromApi()

        coVerify(exactly = 1) { remoteRepository.getFoodItems()  }
        coVerify(exactly = 1) { localRepository.doesItemExists(1) }
        coVerify(exactly = 1) { localRepository.doesItemExists(2) }
        coVerify(exactly = 1) { localRepository.doesItemExists(3) }
        coVerify(exactly = 0) { localRepository.insertFoodItem(match { it.id == 1 }) }
        coVerify(exactly = 0) { localRepository.insertFoodItem(match { it.id == 2 }) }
        coVerify(exactly = 0) { localRepository.insertFoodItem(match { it.id == 3 }) }

        Truth.assertThat(response).isInstanceOf(Resource.Success(Unit)::class.java)
    }

    @Test
    fun `does not insert food items from api returns error on io exception`() = runTest {

        coEvery { remoteRepository.getFoodItems() } returns Resource.Error("Please check your internet connection.")

        val response = insertFoodItemsFromApi()

        coVerify(exactly = 1) { remoteRepository.getFoodItems()  }

        Truth.assertThat(response).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((response as Resource.Error).message).isEqualTo("Please check your internet connection.")
    }

    @Test
    fun `does not insert food items from api returns error on exception`() = runTest {

        coEvery { remoteRepository.getFoodItems() } returns Resource.Error("An unexpected error occurred.")

        val response = insertFoodItemsFromApi()

        coVerify(exactly = 1) { remoteRepository.getFoodItems()  }

        Truth.assertThat(response).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat((response as Resource.Error).message).isEqualTo("An unexpected error occurred.")
    }
}