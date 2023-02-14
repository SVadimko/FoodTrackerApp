package com.vadimko.barcodesearch_data.remote.dto

import com.squareup.moshi.Json

data class NutrientLevels(
    @field:Json(name = "fat")
    val fat: String?,
    @field:Json(name = "saturated-fat")
    val saturatedFat: String?,
    @field:Json(name = "salt")
    val salt: String?,
    @field:Json(name = "sugars")
    val sugars: String?,
)
