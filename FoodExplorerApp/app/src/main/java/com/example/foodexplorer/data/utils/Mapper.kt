package com.example.foodexplorer.data.utils

import com.example.foodexplorer.data.data_source.local.FoodItemEntity
import com.example.foodexplorer.domain.model.FoodItem

fun FoodItemEntity.toModel(): FoodItem{
    return FoodItem(
        description = itemDescription,
        id = itemId,
        name = itemName,
        rating = itemRating,
        imageUrl = itemImageUrl,
        price = itemPrice,
        isFavourite = isFavourite
    )
}

fun FoodItem.toEntity(): FoodItemEntity {
    return FoodItemEntity(
        itemDescription = description,
        itemId = id,
        itemName = name,
        itemRating = rating,
        itemImageUrl = imageUrl,
        itemPrice = price,
        isFavourite = isFavourite
    )
}