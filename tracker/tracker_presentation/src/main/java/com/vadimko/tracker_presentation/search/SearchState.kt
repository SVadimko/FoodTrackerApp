package com.vadimko.tracker_presentation.search


data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFoodUiState> = mutableListOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1
)