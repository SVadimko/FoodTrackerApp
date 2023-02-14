package com.vadimko.foodtrackerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.BarcodeSearchScreen
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.foodtrackerapp.navigation.BlankTempScreen
import com.vadimko.foodtrackerapp.navigation.BottomNavigationBar
import com.vadimko.foodtrackerapp.navigation.NavBarItemsList
import com.vadimko.foodtrackerapp.navigation.Route
import com.vadimko.foodtrackerapp.support.*
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

    private val viewModel: MainVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isLoading.value
        }
        setContent {
            FoodTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                /*      Surface(
                          modifier = Modifier.fillMaxSize(),
                          color = MaterialTheme.colors.background
                      ) {*/
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                Log.wtf("Destination", navBackStackEntry?.destination?.route.toString())
                when (navBackStackEntry?.destination?.route) {
                    Route.TRACKER_OVERVIEW, Route.SEARCH, Route.BARCODE_SEARCH, Route.TEMP_SCREEN -> bottomBarState.value =
                        true
                    else -> bottomBarState.value = false
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                    /* sheetContent = {
                         BottomSheet()
                     },*/
                    bottomBar = {
                        BottomNavigationBar(
                            items = NavBarItemsList.createNavItemsList(),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            },
                            bottomBarState = bottomBarState
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = if (prefs.loadShouldShowOnboarding()) Route.WELCOME else Route.TRACKER_OVERVIEW,
                        modifier = Modifier
                            .padding(padding)

                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.GENDER)
                                }
                            )
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.HEIGHT)
                                }
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNextClick = {
                                navController.navigate(Route.AGE)
                            })
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.WEIGHT)
                                }
                            )
                        }
                        /*    composable(Route.WEIGHT) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                }
                            )
                        }*/
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
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                })

                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = { height ->
                                    navController.navigate(
                                        Route.WEIGHT +
                                                "/$height"
                                    )
                                })
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutritionScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                }
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNextClick = {
                                navController.navigate(Route.GOAL)
                            })
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = { mealName, day, month, year ->
                                    navController.navigate(
                                        Route.SEARCH +
                                                "/$mealName" +
                                                "/$day" +
                                                "/$month" +
                                                "/$year"
                                    )
                                })
                        }
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
                                onNavigateUp = { navController.navigateUp() })

                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            })
                        }
                        composable(Route.TEMP_SCREEN) {
                            BlankTempScreen(
                            )
                        }
                        composable(Route.BARCODE_SEARCH) {
                            BarcodeSearchScreen()
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