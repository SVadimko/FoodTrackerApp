package com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search

sealed class BarcodeSearchEvent {
    data class OnQueryChange(val query: String) : BarcodeSearchEvent()
    object OnSearch: BarcodeSearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean): BarcodeSearchEvent()
}