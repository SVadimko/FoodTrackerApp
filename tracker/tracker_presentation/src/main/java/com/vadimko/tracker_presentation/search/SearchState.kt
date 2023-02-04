package com.vadimko.tracker_presentation.search

import com.vadimko.tracker_domain.model.TrackableFood


data class SearchState(
    val query: String = "",
    val isHintVisible: Boolean = false,
    val isSearching: Boolean = false,
    val trackableFood: List<TrackableFoodUiState> = mutableListOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1,
    val isShowInfo: Boolean = false,
    val infoFood: TrackableFood? = null
)