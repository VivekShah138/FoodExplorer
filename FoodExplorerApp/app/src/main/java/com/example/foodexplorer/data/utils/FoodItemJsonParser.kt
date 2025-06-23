package com.example.foodexplorer.data.utils

import com.example.foodexplorer.domain.model.FoodItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FoodItemJsonParser {

    fun parseJsonToFoodItems(jsonString: String): List<FoodItem> {
        val listType = object : TypeToken<List<FoodItem>>(){}.type
        return Gson().fromJson(jsonString, listType)
    }

}