package com.vadimko.barcodesearch_domain.model

data class BarcodeState(
    val url:List<String?> = emptyList(),
    val resultList: List<Parameter> = emptyList()
)