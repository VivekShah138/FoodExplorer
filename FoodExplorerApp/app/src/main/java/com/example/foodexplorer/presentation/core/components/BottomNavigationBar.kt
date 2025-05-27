package com.example.foodexplorer.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodexplorer.presentation.core.utils.BottomNavItem
import com.example.foodexplorer.presentation.core.utils.BottomNavItemsList

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavItem> = BottomNavItemsList
) {

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 8.dp,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEach { item ->

                    IconButton(onClick = {
                        val screen = item.screens

                        if (navController.currentDestination?.route != screen.routes) {
                            navController.navigate(screen.routes)
                        }
                    }) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (navController.currentDestination?.route == item.screens.routes){
                                MaterialTheme.colorScheme.primary
                            }else{
                                Color.Gray
                            },
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        },
        modifier = Modifier.height(100.dp)
    )
}