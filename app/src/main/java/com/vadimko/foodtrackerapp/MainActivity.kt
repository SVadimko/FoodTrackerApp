package com.vadimko.foodtrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.foodtrackerapp.navigation.BlankTempScreen
import com.vadimko.foodtrackerapp.navigation.BottomNavigationBar
import com.vadimko.foodtrackerapp.navigation.NavBarItemsList
import com.vadimko.foodtrackerapp.navigation.Route
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

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition{
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
                val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
                val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
               val navBackStackEntry by navController.currentBackStackEntryAsState()
                val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
                when (navBackStackEntry?.destination?.route) {
                    Route.TRACKER_OVERVIEW, Route.SEARCH ->  bottomBarState.value = true
                    else -> bottomBarState.value = false
                }
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        BottomSheet()
                    },
                  /*  bottomBar = {
                            BottomNavigationBar(
                                items = NavBarItemsList.createNavItemsList(),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                },
                                bottomBarState = bottomBarState
                            )
                    },*/
                    modifier = Modifier.fillMaxSize(),
                    sheetPeekHeight = 16.dp
                ) { Box {
                    NavHost(
                        navController = navController,
                        startDestination = if (prefs.loadShouldShowOnboarding()) Route.WELCOME else Route.TRACKER_OVERVIEW,
//                        modifier = Modifier
//                            .padding(padding)

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
                            route = Route.WEIGHT + "/{height}",
                            arguments = listOf(
                                navArgument(
                                    name = "height"
                                ) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val height = it.arguments?.getString("height")!!
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
                            GoalScreen(onNextClick = {
                                navController.navigate(Route.NUTRIENT_GOAL)
                            })
                        }
                        composable(Route.TEMP_SCREEN) {
                            BlankTempScreen(
                            )
                        }
                    }
                    //  }
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