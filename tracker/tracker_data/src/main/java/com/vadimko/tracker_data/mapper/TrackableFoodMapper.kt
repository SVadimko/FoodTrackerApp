package com.vadimko.tracker_data.mapper

import com.vadimko.tracker_data.remote.dto.Product
import com.vadimko.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood? {
    return TrackableFood(
        name = this.productName ?: return null,
        imageUrl = this.imageFrontThumbUrl,
        caloriesPer100g = this.nutriments.energyKcal100g.roundToInt(),
        fatPer100g =this.nutriments.fat100g.roundToInt(),
        proteinPer100g =this.nutriments.proteins100g.roundToInt(),
        carbsPer100g =this.nutriments.carbohydrates100g.roundToInt(),
    )
}