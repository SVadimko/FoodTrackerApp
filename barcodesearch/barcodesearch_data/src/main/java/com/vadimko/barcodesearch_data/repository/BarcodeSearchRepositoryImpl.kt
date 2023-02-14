package com.vadimko.barcodesearch_data.repository

import android.util.Log
import com.vadimko.barcodesearch_data.mapper.toBarcodeResult
import com.vadimko.barcodesearch_data.remote.OpenFoodBarCodeApi
import com.vadimko.barcodesearch_domain.model.BarcodeResult
import com.vadimko.barcodesearch_domain.repository.BarcodeSearchRepository

class BarcodeSearchRepositoryImpl(
    private val barcodeApi: OpenFoodBarCodeApi
) : BarcodeSearchRepository {
    override suspend fun barcodeSearch(barcode: String): Result<BarcodeResult> {
        return try {
            val response = barcodeApi.barcodeResponse(
                barcode = barcode
            )
            Result.success(response.product.toBarcodeResult())
        } catch (err: Exception) {
            Log.e("TF_barcodeSearch", err.message.toString())
            Result.failure(err)
        }
    }
}