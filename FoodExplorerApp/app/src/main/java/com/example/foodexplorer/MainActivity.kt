package com.example.foodexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodexplorer.presentation.all_food_items.AllFoodItemsViewModel
import com.example.foodexplorer.presentation.fav_food_items.FavFoodItemsViewModel
import com.example.foodexplorer.presentation.single_food_item.SingleFoodItemViewModel
import com.example.foodexplorer.presentation.all_food_items.AllFoodItemScreen
import com.example.foodexplorer.presentation.fav_food_items.FavFoodItemsScreen
import com.example.foodexplorer.presentation.single_food_item.SingleFoodItemScreen
import com.example.foodexplorer.presentation.core.utils.Screens
import com.example.foodexplorer.ui.theme.FoodExplorerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodExplorerTheme {

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screens.AllFoodItemsScreen.routes
                ) {

                    composable(
                        route = Screens.AllFoodItemsScreen.routes
                    ) {
                        val viewModel: AllFoodItemsViewModel = hiltViewModel()
                        val states by viewModel.allFoodItemsState.collectAsStateWithLifecycle()
                        val onEvent = viewModel::onEvent
                        AllFoodItemScreen(
                            navController = navController,
                            state = states,
                            onEvent = onEvent
                        )
                    }

                    composable(
                        route = Screens.FavFoodItemsScreen.routes
                    ) {
                        val viewModel: FavFoodItemsViewModel = hiltViewModel()
                        val states by viewModel.favFoodItemsState.collectAsStateWithLifecycle()
                        val onEvent = viewModel::onEvent
                        FavFoodItemsScreen (
                            navController = navController,
                            state = states,
                            onEvent = onEvent
                        )
                    }

                    composable(
                        route = "${Screens.FoodItemDetailsScreen.routes}/{itemId}",
                        arguments = listOf(navArgument("itemId"){type = NavType.IntType})
                    ) {


                        val viewModel: SingleFoodItemViewModel = hiltViewModel()
                        val states by viewModel.singleFoodItemsState.collectAsStateWithLifecycle()
                        val onEvent = viewModel::onEvent
                        SingleFoodItemScreen (
                            navController = navController,
                            state = states,
                            onEvent = onEvent
                        )
                    }


                }

            }
        }
    }
}
