package com.vadimko.foodtrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vadimko.core.navigation.Route
import com.vadimko.foodtrackerapp.navigation.navigate
import com.vadimko.foodtrackerapp.ui.theme.FoodTrackerAppTheme
import com.vadimko.onboarding_presentation.age.AgeScreen
import com.vadimko.onboarding_presentation.gender.GenderScreen
import com.vadimko.onboarding_presentation.welcome.TempAgeScreen
import com.vadimko.onboarding_presentation.welcome.WelcomeScreen
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                            startDestination = Route.WELCOME
                        ) {

                            composable(Route.WELCOME) {
                                WelcomeScreen(onNavigate = navController::navigate)
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

                            }
                            composable(Route.WEIGHT) {

                            }
                            composable(Route.NUTRIENT_GOAL) {

                            }
                            composable(Route.ACTIVITY) {

                            }
                            composable(Route.TRACKER_OVERVIEW) {

                            }
                            composable(Route.SEARCH) {

                            }
                            composable(Route.GOAL) {

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