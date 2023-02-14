package com.vadimko.barcodesearch_domain.use_case

import com.vadimko.barcodesearch_domain.model.BarcodeResult
import com.vadimko.barcodesearch_domain.repository.BarcodeSearchRepository

class BarcodeSearch(
    private val repo: BarcodeSearchRepository
) {
    suspend operator fun invoke(
        barcode: String
    ): Result<BarcodeResult?> {
        if (barcode.isBlank()) {
            return Result.success(null)
        }
        return repo.barcodeSearch(barcode.trim())
    }
}