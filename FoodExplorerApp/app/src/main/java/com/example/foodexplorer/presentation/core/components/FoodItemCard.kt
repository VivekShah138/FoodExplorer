package com.example.foodexplorer.presentation.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.foodexplorer.R
import com.example.foodexplorer.domain.model.FoodItem
import com.example.foodexplorer.ui.theme.StarColor

@Composable
fun FoodItemCard(
    item: FoodItem,
    onItemClick: () -> Unit,
    onFavClick: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable { onItemClick() },
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.imageUrl,
                contentDescription = "Food Image",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.no_internet),
                error = painterResource(id = R.drawable.no_internet)
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.description,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Icon(
                        imageVector = if (item.isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite_Item",
                        tint = Color.Red,
                        modifier = Modifier.clickable { onFavClick(item.isFavourite)  }
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "item_rating",
                            tint = StarColor,
                            modifier = Modifier.scale(0.8f)
                        )
                        Text(text = item.rating.toString(), style = MaterialTheme.typography.bodySmall)
                    }

                    Text(
                        text = "$ ${item.price}",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun FoodItemCardPreview() {

    FoodItemCard(
        item = FoodItem(
            id = 1,
            name = "Spicy Chicken Burger",
            description = "DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
            isFavourite = false,
            rating = 4.0,
            price = 29.99,
            imageUrl = "https://foodish-api.com/images/pizza/pizza5.jpg"
        ),
        onItemClick = {},
        onFavClick = {}
    )
}
