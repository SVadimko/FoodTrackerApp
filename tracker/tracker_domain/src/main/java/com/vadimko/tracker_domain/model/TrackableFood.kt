package com.vadimko.tracker_domain.model

data class TrackableFood(
val name: String,
val imageUrl: String? = null,
val caloriesPer100g: Int,
val carbsPer100g: Int,
val proteinPer100g: Int,
val fatPer100g: Int
)