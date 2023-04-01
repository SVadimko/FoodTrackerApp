package com.vadimko.foodtrackerapp.navigation

import androidx.compose.material.ScaffoldState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.BarcodeSearchScreen
import com.vadimko.foodtrackerapp.support.*
import com.vadimko.onboarding_presentation.age.AgeScreen
import com.vadimko.onboarding_presentation.avtivity.ActivityScreen
import com.vadimko.onboarding_presentation.gender.GenderScreen
import com.vadimko.onboarding_presentation.goal.GoalScreen
import com.vadimko.onboarding_presentation.height.HeightScreen
import com.vadimko.onboarding_presentation.nutrition.NutritionScreen
import com.vadimko.onboarding_presentation.weight.WeightScreen
import com.vadimko.onboarding_presentation.welcome.WelcomeScreen
import com.vadimko.tracker_presentation.search.SearchScreen
import com.vadimko.tracker_presentation.tracker_overview.TrackerOverviewScreen


fun NavGraphBuilder.welcomeScreen(scaffoldState: ScaffoldState, onNextClick: ()->Unit){
composable(Route.WELCOME){
    WelcomeScreen(
        scaffoldState,
        onNextClick = onNextClick
    )
}
}

fun NavGraphBuilder.ageScreen(scaffoldState: ScaffoldState, onNextClick: ()->Unit){
    composable(Route.AGE){
        AgeScreen(
            scaffoldState,
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.genderScreen( onNextClick: ()->Unit){
    composable(Route.GENDER){
        GenderScreen(
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.heightScreen(scaffoldState: ScaffoldState, onNextClick: (String)->Unit){
    composable(Route.HEIGHT){
        HeightScreen(
            scaffoldState = scaffoldState,
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.weightScreen(scaffoldState: ScaffoldState,  onNextClick: ()->Unit ){
    composable(
        route = Route.WEIGHT + "/{$HEIGHT}",
        arguments = listOf(
            navArgument(
                name = HEIGHT
            ) {
                type = NavType.StringType
            }
        )
    ) {
        val height = it.arguments?.getString(HEIGHT)!!
        WeightScreen(
            scaffoldState = scaffoldState,
            height = height,
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.activityScreen(onNextClick: ()->Unit){
    composable(Route.ACTIVITY) {
        ActivityScreen(onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.trackerOverviewScreen(onNavigateSearch:(String, Int, Int, Int) -> Unit){
    composable(Route.TRACKER_OVERVIEW) {
        TrackerOverviewScreen(
            onNavigateToSearch = onNavigateSearch
        )
    }
}

fun NavGraphBuilder.nutritionScreen(scaffoldState: ScaffoldState, onNextClick: ()->Unit){
    composable(Route.NUTRIENT_GOAL) {
        NutritionScreen(
            scaffoldState = scaffoldState,
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.goalScreen( onNextClick: ()->Unit){
    composable(Route.GOAL) {
        GoalScreen(
            onNextClick = onNextClick
        )
    }
}

fun NavGraphBuilder.barcodeSearchScreen(){
    composable(Route.BARCODE_SEARCH) {
        BarcodeSearchScreen(
        )
    }
}

fun NavGraphBuilder.tempScreen(){
    composable(Route.TEMP_SCREEN) {
        BlankTempScreen(
        )
    }
}


fun NavGraphBuilder.searchScreen(scaffoldState: ScaffoldState,  onNavigateUp: ()->Unit ){
    composable(
        route = Route.SEARCH + "/{$MEAL_NAME}/{$DAY_OF_MONTH}/{$MONTH}/{$YEAR}",
        arguments = listOf(
            navArgument(
                name = MEAL_NAME
            ) {
                type = NavType.StringType
            },
            navArgument(
                name = DAY_OF_MONTH
            ) {
                type = NavType.IntType
            },
            navArgument(
                name = MONTH
            ) {
                type = NavType.IntType
            },
            navArgument(
                name = YEAR
            ) {
                type = NavType.IntType
            },
            )
    ) {
        val mealType = it.arguments?.getString(MEAL_NAME)!!
        val dayOfMonth = it.arguments?.getInt(DAY_OF_MONTH)!!
        val month = it.arguments?.getInt(MONTH)!!
        val year = it.arguments?.getInt(YEAR)!!
        SearchScreen(
            scaffoldState = scaffoldState,
            mealName = mealType,
            dayOfMonth = dayOfMonth,
            month = month,
            year = year,
            onNavigateUp = onNavigateUp
        )
    }
    }


fun NavController.navigateToWelcomeScreen(){
    navigate(Route.WELCOME)
}

fun NavController.navigateToGenderScreen(){
    navigate(Route.GENDER)
}

fun NavController.navigateToAgeScreen(){
    navigate(Route.AGE)
}

fun NavController.navigateToHeightScreen(){
    navigate(Route.HEIGHT)
}

fun NavController.navigateToWeightScreen(height: String){
    navigate(Route.WEIGHT +"/$height")
}

fun NavController.navigateToActivityScreen(){
    navigate(Route.ACTIVITY)
}

fun NavController.navigateToTrackerOverViewScreen(){
    navigate(Route.TRACKER_OVERVIEW)
}

fun NavController.navigateToNutrientCoalScreen(){
    navigate(Route.NUTRIENT_GOAL)
}

fun NavController.navigateToSearchScreen(mealName: String, day: Int, month: Int, year: Int){
    navigate(
        Route.SEARCH +
                "/$mealName" +
                "/$day" +
                "/$month" +
                "/$year"
    )
}

fun NavController.navigateBackToTrackerOverViewScreen(){
    navigateUp()
}

