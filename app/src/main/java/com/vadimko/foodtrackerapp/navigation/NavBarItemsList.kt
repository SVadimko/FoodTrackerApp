package com.vadimko.foodtrackerapp.navigation

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search

object NavBarItemsList {

    fun createNavItemsList(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                name = "Home",
                route = Route.TRACKER_OVERVIEW,
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                name = "Home2",
                route = Route.TEMP_SCREEN,
                icon = Icons.Default.Search
            ),
            BottomNavItem(
                name = "Home3",
                route = Route.TEMP_SCREEN,
                icon = Icons.Default.Add
            )
        )
    }
}