package com.vadimko.barcodesearch_data.remote

import com.vadimko.barcodesearch_data.remote.dto.BarcodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodBarCodeApi {

    @GET("/api/v2/product/{barcode}")
    suspend fun barcodeResponse(
        @Path("barcode") barcode: String,
    ) : BarcodeResponse

    companion object  {
        const val BASE_URL = "https://world.openfoodfacts.org/"
    }
}