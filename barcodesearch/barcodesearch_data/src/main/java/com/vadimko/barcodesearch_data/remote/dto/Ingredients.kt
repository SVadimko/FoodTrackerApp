package com.vadimko.barcodesearch_data.remote.dto

import com.squareup.moshi.Json

data class Ingredients(
    @field:Json(name = "text")
    val text:String,
    @field:Json(name = "percent_estimate")
    val percent_estimate:Double,
)
