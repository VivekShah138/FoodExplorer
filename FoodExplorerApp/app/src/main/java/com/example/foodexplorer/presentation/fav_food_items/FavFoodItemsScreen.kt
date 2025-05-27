package com.example.foodexplorer.presentation.fav_food_items

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodexplorer.presentation.core.components.AppTopBar
import com.example.foodexplorer.presentation.core.components.BottomNavigationBar
import com.example.foodexplorer.presentation.core.components.FoodItemCard
import com.example.foodexplorer.presentation.core.utils.Screens

@Composable
fun FavFoodItemsScreen(
    navController: NavController,
    state: FavFoodItemsState,
    onEvent: (FavFoodItemsEvents) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Favourites",
                onBackClick = {}
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ){
                if(state.favFoodItemsList.isEmpty()){
                    Text(
                        text = "No Favourite Items",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
                else{
                    if(!isFocused){
                        LazyColumn(

                        ) {
                            items(state.favFoodItemsList){ foodItems ->
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
                                            onEvent(FavFoodItemsEvents.UpdateFavState(itemId = foodItems.id,isFav = !it))
                                        }
                                    )
                                }

                            }
                        }
                    }
                    else{

                    }
                }
            }
        }
    }

}