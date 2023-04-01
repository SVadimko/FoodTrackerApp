package com.vadimko.foodtrackerapp.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.foodtrackerapp.navigation.*
import com.vadimko.foodtrackerapp.support.*

@Composable
fun AppNavigation(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    prefs: Preferences,
    padding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = if (prefs.loadShouldShowOnboarding()) Route.WELCOME else Route.TRACKER_OVERVIEW,
        modifier = Modifier
            .padding(padding)
    ){
        welcomeScreen(scaffoldState, onNextClick={navController.navigateToGenderScreen()})
        ageScreen(scaffoldState, onNextClick = {navController.navigateToHeightScreen()})
        genderScreen(onNextClick = {navController.navigateToAgeScreen()})
        heightScreen(scaffoldState, onNextClick = {height -> navController.navigateToWeightScreen(height)})
        weightScreen(scaffoldState, onNextClick = {navController.navigateToActivityScreen()})
        activityScreen ( onNextClick = {navController.navigateToTrackerOverViewScreen()} )
        nutritionScreen(scaffoldState, onNextClick = {navController.navigateToTrackerOverViewScreen()})
        trackerOverviewScreen(onNavigateSearch = {mealName, day, month, year -> navController.navigateToSearchScreen(mealName, day, month, year)})
        searchScreen(scaffoldState, onNavigateUp = {navController.navigateBackToTrackerOverViewScreen()} )
        goalScreen(onNextClick = {navController.navigateToNutrientCoalScreen()})
        barcodeSearchScreen()
        tempScreen()
    }
}