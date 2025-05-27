package com.example.foodexplorer.data.repository

import android.content.Context
import com.example.foodexplorer.data.data_source.local.FoodItemDao
import com.example.foodexplorer.domain.repository.FoodItemLocalRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FoodItemLocalRepositoryImplTest {

    private lateinit var foodItemDao: FoodItemDao
    private lateinit var context: Context
    private lateinit var repository: FoodItemLocalRepository

    @Before
    fun setUp() {
        foodItemDao  = mockk<FoodItemDao>(relaxed = true)
        context = mockk<Context>(relaxed = true)
        repository = FoodItemLocalLocalRepositoryImpl(context,foodItemDao)
    }

    @Test
    fun `insert food items from assets inserts items that do not exist`() = runTest {
        val jsonString = """
        [
          {
            "id": 1,
            "name": "Pizza Margherita",
            "imageUrl": "https://foodish-api.com/images/pizza/pizza5.jpg",
            "description": "A timeless Italian classic, Pizza Margherita features a thin crust topped with tangy tomato sauce, fresh mozzarella cheese, and aromatic basil leaves.",
            "rating": 4,
            "price": 8.99
          }
        ]
    """.trimIndent()

        // Mock AssetManager
        val assetManager = mockk<android.content.res.AssetManager>(relaxed = true)
        every { context.assets } returns assetManager
        every { assetManager.open("food_items.json") } returns jsonString.byteInputStream()

        coEvery { foodItemDao.doesItemExists(1) } returns false

        repository.insertFoodItemsFromAssets()

        coVerify(exactly = 1) { foodItemDao.doesItemExists(1) }
        coVerify(exactly = 1) { foodItemDao.insertItem(match { it.itemId == 1 }) }
    }

    @Test
    fun `insert food items from assets inserts items that do exist`() = runTest {
        val jsonString = """
        [
          {
            "id": 1,
            "name": "Pizza Margherita",
            "imageUrl": "https://foodish-api.com/images/pizza/pizza5.jpg",
            "description": "A timeless Italian classic, Pizza Margherita features a thin crust topped with tangy tomato sauce, fresh mozzarella cheese, and aromatic basil leaves.",
            "rating": 4,
            "price": 8.99
          }
        ]
    """.trimIndent()


        // Mock AssetManager
        val assetManager = mockk<android.content.res.AssetManager>(relaxed = true)
        every { context.assets } returns assetManager
        every { assetManager.open("food_items.json") } returns jsonString.byteInputStream()

        coEvery { foodItemDao.doesItemExists(1) } returns true

        repository.insertFoodItemsFromAssets()

        coVerify(exactly = 1) { foodItemDao.doesItemExists(1) }
        coVerify(exactly = 0) { foodItemDao.insertItem(any()) }
    }
}
