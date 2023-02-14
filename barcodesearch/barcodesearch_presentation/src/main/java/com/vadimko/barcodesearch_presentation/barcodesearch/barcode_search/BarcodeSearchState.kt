package com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search

import com.vadimko.barcodesearch_domain.model.BarcodeState

data class BarcodeSearchState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val error: Boolean? = null,
    val resultList: BarcodeState = BarcodeState(emptyList(), emptyList()),
    val isShowInfo: Boolean = false,
)


