package com.example.foodexplorer.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FoodItemEntity::class],
    version = 1
)
abstract class FoodItemDatabase: RoomDatabase() {
    abstract val foodItemDao: FoodItemDao
    companion object {
        const val DATABASE_NAME = "food_item_db"
    }
}