package com.vadimko.tracker_domain.use_case

data class TrackerUseCase(
    val trackFood: TrackFood,
    val searchFood: SearchFood,
    val deleteFood: DeleteFood,
    val getFoodForDate: GetFoodForDate,
    val calculateMealNutrients: CalculateMealNutrients
)
