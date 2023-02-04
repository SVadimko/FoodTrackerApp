package com.vadimko.tracker_data.mapper

import com.vadimko.tracker_data.remote.dto.Product
import com.vadimko.tracker_domain.model.AdditionalNutriments
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
        additionalNutriments = AdditionalNutriments(
            alcohol = this.nutriments.alcohol,
            calcium = this.nutriments.calcium,
            cellulose = this.nutriments.cellulose,
            cu = this.nutriments.cu,
            iron = this.nutriments.iron,
            fiber = this.nutriments.fiber,
            magnesium = this.nutriments.magnesium,
            phosphorus = this.nutriments.phosphorus,
            potassium = this.nutriments.potassium,
            omega3 = this.nutriments.omega3,
            omega6 = this.nutriments.omega6,
            salt = this.nutriments.salt,
            saturatedFat = this.nutriments.saturatedFat,
            sodium = this.nutriments.sodium,
            sugars = this.nutriments.sugars,
            vitaminC = this.nutriments.vitaminC,
            zinc = this.nutriments.zinc,
        )
    )
}