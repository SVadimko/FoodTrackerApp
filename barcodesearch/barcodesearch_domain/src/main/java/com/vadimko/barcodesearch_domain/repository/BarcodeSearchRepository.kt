package com.vadimko.barcodesearch_domain.repository

import com.vadimko.barcodesearch_domain.model.BarcodeResult

interface BarcodeSearchRepository {

    suspend fun barcodeSearch(
        barcode: String,
    ): Result<BarcodeResult>
}