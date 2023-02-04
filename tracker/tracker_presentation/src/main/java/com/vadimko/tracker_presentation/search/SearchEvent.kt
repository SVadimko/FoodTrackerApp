package com.vadimko.tracker_presentation.search

import com.vadimko.tracker_domain.model.MealType
import com.vadimko.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent()
    data class OnSearch(val page: Int): SearchEvent()
    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class OnAmountFoodForChange(
        val food: TrackableFood,
        val amount: String
    ) : SearchEvent()
    data class OnTrackFoodClick(
        val food: TrackableFood,
        val mealType: MealType,
        val date: LocalDate
    ): SearchEvent()
    data class OnInfoTrackFoodClick(
        val food: TrackableFood
    ): SearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean): SearchEvent()
    object OnInfoClose: SearchEvent()
}
