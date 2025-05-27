package com.example.foodexplorer.data.data_source

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.foodexplorer.data.data_source.local.FoodItemDao
import com.example.foodexplorer.data.data_source.local.FoodItemDatabase
import com.example.foodexplorer.data.utils.toEntity
import com.example.foodexplorer.domain.model.FoodItem
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class FoodItemDaoTest {

    private lateinit var database: FoodItemDatabase
    private lateinit var foodItemDao: FoodItemDao

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodItemDatabase::class.java
        ).allowMainThreadQueries().build()

        foodItemDao = database.foodItemDao
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertItemList() = runTest {
        val foodItemEntity1 = FoodItem(
            id = 1,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity2 = FoodItem(
            id = 2,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItem3 = FoodItem(
            id = 3,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        )

        foodItemDao.insertItemList(foodItemEntity1)

        val allFoodItems = foodItemDao.getAllItems().first()

        Truth.assertThat(allFoodItems).contains(foodItemEntity1)
    }


    @Test
    fun getAllItems() = runTest {
        val foodItemEntity1 = FoodItem(
            id = 1,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity2 = FoodItem(
            id = 2,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity3 = FoodItem(
            id = 3,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()

        val listOfAllItems = listOf(foodItemEntity1,foodItemEntity2,foodItemEntity3)

        listOfAllItems.forEach {
            foodItemDao.insertItemList(it)
        }

        val listOfAllItemsRetrieved = foodItemDao.getAllItems().first()

        Truth.assertThat(listOfAllItemsRetrieved).containsExactlyElementsIn(listOfAllItems)
    }

    @Test
    fun getAllFavItems() = runTest {
        val foodItemEntity1 = FoodItem(
            id = 1,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity2 = FoodItem(
            id = 2,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = true,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity3 = FoodItem(
            id = 3,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()

        val listOfAllItems = listOf(foodItemEntity1,foodItemEntity2,foodItemEntity3)

        listOfAllItems.forEach {
            foodItemDao.insertItemList(it)
        }

        val listOfAllFavItemsRetrieved = foodItemDao.getAllFavouriteItems().first()

        Truth.assertThat(listOfAllFavItemsRetrieved).contains(foodItemEntity2)
    }

    @Test
    fun updateItemToFavorite() = runTest {
        val foodItemEntity1 = FoodItem(
            id = 1,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity2 = FoodItem(
            id = 2,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = true,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity3 = FoodItem(
            id = 3,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()

        val listOfAllItems = listOf(foodItemEntity1,foodItemEntity2,foodItemEntity3)

        listOfAllItems.forEach {
            foodItemDao.insertItemList(it)
        }

        foodItemDao.updateFavoriteItem(id = 3, isFav = true)

        val listOfAllFavItemsRetrieved = foodItemDao.getAllFavouriteItems().first()

        Truth.assertThat(listOfAllFavItemsRetrieved.map { it.itemId }).containsExactly(2, 3)

    }

    @Test
    fun removeItemFromFavorite() = runTest {
        val foodItemEntity1 = FoodItem(
            id = 1,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity2 = FoodItem(
            id = 2,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = true,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity3 = FoodItem(
            id = 3,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()

        val listOfAllItems = listOf(foodItemEntity1,foodItemEntity2,foodItemEntity3)

        listOfAllItems.forEach {
            foodItemDao.insertItemList(it)
        }

        foodItemDao.updateFavoriteItem(id = 2, isFav = false)

        val listOfAllFavItemsRetrieved = foodItemDao.getAllFavouriteItems().first()

        Truth.assertThat(listOfAllFavItemsRetrieved).isEmpty()
    }

    @Test
    fun insertItemWhichDoesNotExists() = runTest {
        val foodItemEntity1 = FoodItem(
            id = 1,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity2 = FoodItem(
            id = 2,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = true,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity3 = FoodItem(
            id = 3,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity4 = FoodItem(
            id = 4,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()
        val foodItemEntity5 = FoodItem(
            id = 5,
            name = "Pizza Margherita",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 200.0,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ).toEntity()

        val listOfAllItems = listOf(foodItemEntity1,foodItemEntity3,foodItemEntity5)

        listOfAllItems.forEach {
            foodItemDao.insertItemList(it)
        }

        val listOfAllItems2 = listOf(foodItemEntity1,foodItemEntity2,foodItemEntity3,foodItemEntity4,foodItemEntity5)

        listOfAllItems2.forEach {
            val doesExists = foodItemDao.doesItemExists(it.itemId)
            if(!doesExists){
                foodItemDao.insertItemList(it)
            }
        }

        val listOfAllItemsRetrieved = foodItemDao.getAllItems().first()

        Truth.assertThat(listOfAllItemsRetrieved).hasSize(5)
    }
}