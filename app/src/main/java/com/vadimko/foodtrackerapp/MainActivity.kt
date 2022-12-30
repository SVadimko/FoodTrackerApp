package com.vadimko.foodtrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vadimko.core.navigation.Route
import com.vadimko.foodtrackerapp.navigation.navigate
import com.vadimko.foodtrackerapp.ui.theme.FoodTrackerAppTheme
import com.vadimko.onboarding_presentation.welcome.TempAgeScreen
import com.vadimko.onboarding_presentation.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME
                    ) {

                        composable(Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.AGE) {
                            TempAgeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.GENDER) {

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