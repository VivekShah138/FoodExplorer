package com.example.foodexplorer.presentation.all_food_items

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.presentation.core.components.AppTopBar
import com.example.foodexplorer.presentation.core.components.BottomNavigationBar
import com.example.foodexplorer.presentation.core.components.FoodItemCard
import com.example.foodexplorer.presentation.core.utils.Screens
import com.example.foodexplorer.ui.theme.FoodExplorerTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllFoodItemScreen(
    navController: NavController,
    state: AllFoodItemsState,
    onEvent: (AllFoodItemsEvents) -> Unit
){

    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current
    val swipeRefreshState = rememberPullRefreshState(
        refreshing = state.isListLoading,
        onRefresh = {
            onEvent(AllFoodItemsEvents.LoadAllFoodItemsFromApi)
        }
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Food Items",
                onBackClick = {}
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .pullRefresh(swipeRefreshState)
            ){
                if(!state.isError.isNullOrEmpty()){

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.isError,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        TextButton(
                            onClick = {
                                onEvent(AllFoodItemsEvents.LoadAllFoodItemsFromDb)
                            }
                        ) {
                            Text(
                                text = "Load List From Assets",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                }
                else{
                    if(!isFocused){
                        LazyColumn(

                        ) {
                            items(state.allFoodItemsList){ foodItems ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ){
                                    FoodItemCard(
                                        item = foodItems,
                                        onItemClick = {
                                            navController.navigate("${Screens.FoodItemDetailsScreen.routes}/${foodItems.id}")
                                        },
                                        onFavClick = {
                                            onEvent(AllFoodItemsEvents.UpdateFavState(itemId = foodItems.id,isFav = !it))
                                        }
                                    )
                                }

                            }
                        }
                    }
                    else{

                    }
                }

                PullRefreshIndicator(
                    refreshing = state.isListLoading,
                    state = swipeRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
    }
}


@Preview
@Composable
fun AllFoodItemsScreenPreview(){
    FoodExplorerTheme {
        AllFoodItemScreen(
            navController = rememberNavController(),
            state = AllFoodItemsState(
                allFoodItemsList = listOf(
                    FoodItem(
                        id = 1,
                        name = "Pizza Margherita",
                        description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                        isFavourite = false,
                        rating = 4.0,
                        price = 200.0,
                        imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
                    ),
                    FoodItem(
                        id = 1,
                        name = "Pizza Margherita",
                        description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                        isFavourite = false,
                        rating = 4.0,
                        price = 200.0,
                        imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
                    ),
                    FoodItem(
                        id = 1,
                        name = "Pizza Margherita",
                        description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                        isFavourite = false,
                        rating = 4.0,
                        price = 200.0,
                        imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
                    ),
                    FoodItem(
                        id = 1,
                        name = "Pizza Margherita",
                        description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                        isFavourite = false,
                        rating = 4.0,
                        price = 200.0,
                        imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
                    ),
                    FoodItem(
                        id = 1,
                        name = "Pizza Margherita",
                        description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                        isFavourite = false,
                        rating = 4.0,
                        price = 200.0,
                        imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
                    ),
                    FoodItem(
                        id = 1,
                        name = "Pizza Margherita",
                        description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                        isFavourite = false,
                        rating = 4.0,
                        price = 200.0,
                        imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
                    )
                )
            ),
            onEvent = {}
        )
    }
}