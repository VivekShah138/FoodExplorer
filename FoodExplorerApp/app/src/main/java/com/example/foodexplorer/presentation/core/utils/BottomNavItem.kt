package com.example.foodexplorer.presentation.core.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val screens: Screens,
    val icon: ImageVector,
    val label: String
)

val BottomNavItemsList = listOf(
    BottomNavItem(Screens.AllFoodItemsScreen, icon = Icons.Default.Home, label = "AllFoodItems"),
    BottomNavItem(Screens.FavFoodItemsScreen, icon = Icons.Default.Star, label = "FavFoodItems"),
)