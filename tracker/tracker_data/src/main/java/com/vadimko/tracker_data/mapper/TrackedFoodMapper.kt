package com.vadimko.tracker_data.mapper

import com.vadimko.tracker_data.local.entity.TrackedFoodEntity
import com.vadimko.tracker_domain.model.MealType
import com.vadimko.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = this.name,
        carbs = this.carbs,
        protein = this.protein,
        fat = this.fat,
        imageUrl = this.imageUrl,
        mealType = MealType.fromString(this.type),
        amount = this.amount,
        date = LocalDate.of(this.year, this.month, this.dayOfMonth),
        calories = this.calories,
        id = this.id
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = this.name,
        carbs = this.carbs,
        protein = this.protein,
        fat = this.fat,
        imageUrl = this.imageUrl,
        type = this.mealType.name,
        amount = this.amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        calories = this.calories,
        id = this.id
    )
}