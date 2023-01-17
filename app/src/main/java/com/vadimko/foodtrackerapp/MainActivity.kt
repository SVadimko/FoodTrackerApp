package com.vadimko.foodtrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.core.navigation.Route
import com.vadimko.foodtrackerapp.navigation.navigate
import com.vadimko.foodtrackerapp.ui.theme.FoodTrackerAppTheme
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
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var prefs: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                /*      Surface(
                          modifier = Modifier.fillMaxSize(),
                          color = MaterialTheme.colors.background
                      ) {*/
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (prefs.loadShouldShowOnboarding()) Route.WELCOME else Route.TRACKER_OVERVIEW
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate)
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutritionScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigate = navController::navigate)
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument(
                                    name = "mealName"
                                ) {
                                    type = NavType.StringType
                                },
                                navArgument(
                                    name = "dayOfMonth"
                                ) {
                                    type = NavType.IntType
                                },
                                navArgument(
                                    name = "month"
                                ) {
                                    type = NavType.IntType
                                },
                                navArgument(
                                    name = "year"
                                ) {
                                    type = NavType.IntType
                                },

                                )
                        ) {
                            val mealType = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealType,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = { navController.navigateUp() })

                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNavigate = navController::navigate)
                        }
                    }
                    //  }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = name)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodTrackerAppTheme {

    }
}