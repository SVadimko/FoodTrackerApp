package com.vadimko.barcodesearch_data.remote.dto

import com.squareup.moshi.Json

data class BarcodeResponse(
    val product: Product,
    @field:Json(name = "status")
    val status: Int?
)
