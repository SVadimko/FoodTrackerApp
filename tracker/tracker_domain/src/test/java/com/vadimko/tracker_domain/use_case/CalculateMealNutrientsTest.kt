package com.vadimko.tracker_domain.use_case

import com.google.common.truth.Truth.assertThat
import com.vadimko.core.domain.model.ActivityLevel
import com.vadimko.core.domain.model.Gender
import com.vadimko.core.domain.model.GoalType
import com.vadimko.core.domain.model.UserInfo
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.tracker_domain.model.MealType
import com.vadimko.tracker_domain.model.TrackedFood
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import kotlin.math.roundToInt
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients
    private lateinit var preferences: Preferences

    @Before
    fun setUp() {
        preferences = mockk<Preferences>(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            name = "SomeName",
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )
        calculateMealNutrients = CalculateMealNutrients(preferences)
    }

    @Test
    fun `Calories for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }
        val result = calculateMealNutrients(trackedFoods)
        val breakfastCalories =
            result.mealNutrients.values.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.calories }
        val expectedCalories =
            trackedFoods.filter { it.mealType is MealType.Breakfast }.sumOf { it.calories }

        assertThat(breakfastCalories).isEqualTo(expectedCalories)

    }

    @Test
    fun `Carbs for breakfast properly calculated`() {
        val trackedFoods = (1..30).map {
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        }
        val result = calculateMealNutrients(trackedFoods)
        val breakfastCarbs =
            result.mealNutrients.values.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.carbs }
        val expectedCarbs =
            trackedFoods.filter { it.mealType is MealType.Breakfast }.sumOf { it.carbs }

        assertThat(breakfastCarbs).isEqualTo(expectedCarbs)

    }

    @Test
    fun `Proper calculation for calories goal`() {
        val trackedFoods = listOf(
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        )
        val calcGoal = calculateMealNutrients.invoke(trackedFoods).caloriesGoal
        val userInfo = preferences.loadUserInfo()
        val activityFactor = when (userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when (userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        val expectedGoal = (((66.47f + 13.75f * preferences.loadUserInfo().weight +
                5f * preferences.loadUserInfo().height - 6.75f * preferences.loadUserInfo().age).roundToInt()) * activityFactor + caloryExtra).roundToInt()
        assertThat(calcGoal).isEqualTo(expectedGoal)
    }

    @Test
    fun `Proper calculation for protein goal`() {
        val trackedFoods = listOf(
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        )
        val calcProteinGoal = calculateMealNutrients.invoke(trackedFoods).proteinGoal
        val userInfo = preferences.loadUserInfo()
        val activityFactor = when (userInfo.activityLevel) {
            is ActivityLevel.Low -> 1.2f
            is ActivityLevel.Medium -> 1.3f
            is ActivityLevel.High -> 1.4f
        }
        val caloryExtra = when (userInfo.goalType) {
            is GoalType.LoseWeight -> -500
            is GoalType.KeepWeight -> 0
            is GoalType.GainWeight -> 500
        }
        val expectedProteinGoal = ((((66.47f + 13.75f * userInfo.weight +
                5f * userInfo.height - 6.75f * userInfo.age).roundToInt()) * activityFactor + caloryExtra).roundToInt() * userInfo.proteinRatio / 4f).roundToInt()

        assertThat(calcProteinGoal).isEqualTo(expectedProteinGoal)
    }

    @Test
    fun `Proper total calories calculation`(){
        val trackedFoods = listOf(
            TrackedFood(
                name = "name",
                carbs = Random.nextInt(100),
                protein = Random.nextInt(100),
                fat = Random.nextInt(100),
                mealType = MealType.fromString(
                    listOf("Breakfast", "Lunch", "Dinner", "Snack").random()
                ),
                imageUrl = null,
                amount = 100,
                date = LocalDate.now(),
                calories = Random.nextInt(2000)
            )
        )
        val result = calculateMealNutrients(trackedFoods).totalCalories
        val expectedResult =  trackedFoods
            .groupBy { it.mealType }
            .mapValues { entry ->
                val type = entry.key
                val foods = entry.value
                CalculateMealNutrients.MealNutrients(
                    carbs = foods.sumOf { it.carbs },
                    protein = foods.sumOf { it.protein },
                    fat = foods.sumOf { it.fat },
                    calories = foods.sumOf { it.calories },
                    mealType = type
                )
            }.values.sumOf { it.calories }

        assertThat(result).isEqualTo(expectedResult)
    }

}