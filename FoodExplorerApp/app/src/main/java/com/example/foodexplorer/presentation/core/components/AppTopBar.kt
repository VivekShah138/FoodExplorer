package com.example.foodexplorer.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.foodexplorer.ui.theme.FoodExplorerTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    titleContentColor: Color = MaterialTheme.colorScheme.onBackground,
    navigationIconContentColor: Color = MaterialTheme.colorScheme.onBackground,

) {

    TopAppBar(
        title = {
            if (showBackButton) Text(text = title, style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center,modifier = Modifier.padding(vertical = 5.dp))
            else Text(text = title, style = TextStyle(fontSize = 20.sp), modifier = Modifier.padding(start = 20.dp).padding(vertical = 5.dp))
        },
        navigationIcon = {
            if(showBackButton){
                IconButton(onClick = { onBackClick() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = navigationIconContentColor,
        )
    )

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePagePreviewScreen() {

    FoodExplorerTheme {
        
        Scaffold(
            topBar = {
                AppTopBar(
                    title = "Title",
                    showBackButton = true,
                    onBackClick = {},
                )
            },
            bottomBar = {
                BottomNavigationBar(rememberNavController())
            }
        ) {padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

            }
        }
    }
}