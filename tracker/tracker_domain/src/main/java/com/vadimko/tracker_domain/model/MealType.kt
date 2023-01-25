package com.vadimko.tracker_domain.model

sealed class MealType(val name: String) {
    object Breakfast : MealType("Breakfast")
    object Lunch : MealType("Lunch")
    object Dinner : MealType("Dinner")
    object Snack : MealType("Snack")

    companion object {
        fun fromString(name: String): MealType {
            return when (name) {
                Breakfast.name -> Breakfast
                Lunch.name -> Lunch
                Dinner.name -> Dinner
                Snack.name -> Snack
                else -> Breakfast
            }
        }
    }
}
