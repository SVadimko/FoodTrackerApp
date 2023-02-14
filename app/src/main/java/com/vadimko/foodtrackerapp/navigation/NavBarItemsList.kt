package com.vadimko.foodtrackerapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning

object NavBarItemsList {

    fun createNavItemsList(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                name = "Track Food",
                route = Route.TRACKER_OVERVIEW,
                icon = Icons.Default.Home
            ),
            BottomNavItem(
                name = "BarCode Search",
                route = Route.BARCODE_SEARCH,
                icon = Icons.Default.Search
            ),
            BottomNavItem(
                name = "Under construction",
                route = Route.TEMP_SCREEN,
                icon = Icons.Default.Warning
            )
        )
    }
}