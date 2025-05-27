package com.example.foodexplorer.data.data_source.local


import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodItemDao {

    @Query("SELECT * FROM FoodItemEntity")
    fun getAllItems(): Flow<List<FoodItemEntity>>

    @Query("SELECT * FROM FoodItemEntity WHERE isFavourite == 1")
    fun getAllFavouriteItems(): Flow<List<FoodItemEntity>>

    @Query("SELECT * FROM FoodItemEntity WHERE itemId = :itemId")
    suspend fun getFoodItemById(itemId:Int) : FoodItemEntity

    @Upsert
    suspend fun insertItem(items: FoodItemEntity)

    @Query("UPDATE FoodItemEntity SET isFavourite = :isFav WHERE itemId = :id")
    suspend fun updateFavoriteItem(id: Int, isFav: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM FoodItemEntity WHERE itemId = :itemId)")
    suspend fun doesItemExists(itemId: Int): Boolean

}