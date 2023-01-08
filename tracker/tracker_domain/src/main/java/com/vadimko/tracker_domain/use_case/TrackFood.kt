package com.vadimko.tracker_domain.use_case

import com.vadimko.tracker_domain.model.MealType
import com.vadimko.tracker_domain.model.TrackableFood
import com.vadimko.tracker_domain.model.TrackedFood
import com.vadimko.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFood(
    private val repo: TrackerRepository
) {

    suspend operator fun invoke(
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ){
        repo.insertTrackedFood(
            TrackedFood(
                name = food.name,
                amount = amount,
                carbs = ((food.carbsPer100g/100f)*amount).roundToInt(),
                protein = ((food.proteinPer100g/100f)*amount).roundToInt(),
                fat = ((food.fatPer100g/100f)*amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                date = date,
                calories = ((food.caloriesPer100g/100f)*amount).roundToInt(),
            )
        )

    }

}