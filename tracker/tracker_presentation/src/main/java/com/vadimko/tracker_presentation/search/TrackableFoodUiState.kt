package com.vadimko.tracker_presentation.search

import com.vadimko.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val food: TrackableFood,
    val isExpended: Boolean = false,
    val amount: String = ""
)
