package com.vadimko.barcodesearch_data.remote.dto

import com.squareup.moshi.Json

data class Nutriments(
    @field:Json(name = "carbohydrates_100g")
    val carbohydrates100g: Double,
    @field:Json(name = "energy-kcal_100g")
    val energyKcal100g: Double,
    @field:Json(name = "fat_100g")
    val fat100g: Double,
    @field:Json(name = "proteins_100g")
    val proteins100g: Double,
    @field:Json(name = "alcohol_100g")
    val alcohol: Double,
    @field:Json(name = "calcium_100g")
    val calcium: Double,
    @field:Json(name = "cellulose_100g")
    val cellulose: Double,
    @field:Json(name = "cu_100g")
    val cu: Double,
    @field:Json(name = "iron_100g")
    val iron: Double,
    @field:Json(name = "fiber_100g")
    val fiber: Double,
    @field:Json(name = "magnesium_100g")
    val magnesium: Double,
    @field:Json(name = "phosphorus_100g")
    val phosphorus: Double,
    @field:Json(name = "potassium_100g")
    val potassium: Double,
    @field:Json(name = "omega-3-fat_100g")
    val omega3: Double,
    @field:Json(name = "omega-6-fat_100g")
    val omega6: Double,
    @field:Json(name = "salt_100g")
    val salt: Double,
    @field:Json(name = "saturated-fat_100g")
    val saturatedFat: Double,
    @field:Json(name = "sodium_100g")
    val sodium: Double,
    @field:Json(name = "sugars_100g")
    val sugars: Double,
    @field:Json(name = "vitamin-c_100g")
    val vitaminC: Double,
    @field:Json(name = "zinc_100g")
    val zinc: Double,
)
