package com.example.foodexplorer.presentation.single_food_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.foodexplorer.R
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.presentation.core.components.AppTopBar
import com.example.foodexplorer.presentation.core.components.CustomSwitch
import com.example.foodexplorer.ui.theme.FoodExplorerTheme
import com.example.foodexplorer.ui.theme.StarColor

@Composable
fun SingleFoodItemScreen(
    navController: NavController,
    state: SingleFoodItemsState,
    onEvent:(SingleFoodItemsEvents) -> Unit
){

    val item = state.foodItem

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Item Details",
                onBackClick = {
                    navController.popBackStack()
                },
                showBackButton = true
            )
        }
    ) {paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)

        ){
            if(item != null){

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
                            .background(
                                color = MaterialTheme.colorScheme.background
                            )
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.4f)
                            .padding(horizontal = 20.dp, vertical = 30.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(30.dp))

                    ) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = "Food Image",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.no_internet),
                            error = painterResource(id = R.drawable.no_internet)
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(30.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Text(
                                    text = item.name,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "$ ${item.price}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyMedium,

                                )

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "item_rating",
                                    tint = StarColor
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(item.rating.toString())
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            CustomSwitch(
                                text = "Add To Favourites",
                                isCheck = state.isFav,
                                onCheckChange = {
                                    onEvent(SingleFoodItemsEvents.ChangeToggleState(it))
                                    onEvent(SingleFoodItemsEvents.UpdateFavState(itemId = item.id, isFav = it))
                                }
                            )
                        }

                    }
                }
            }else{
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
fun SingleFoodItemScreenPreview(){
    FoodExplorerTheme {

        SingleFoodItemScreen(
            navController = rememberNavController(),
            state = SingleFoodItemsState(
                foodItem =  FoodItem(
                    id = 1,
                    name = "Pizza Margherita",
                    description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                    isFavourite = false,
                    rating = 4.0,
                    price = 200.0,
                    imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
                )
            ),
            onEvent = {}
        )

    }
}