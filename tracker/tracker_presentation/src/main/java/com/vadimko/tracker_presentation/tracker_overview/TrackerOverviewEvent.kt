package com.vadimko.tracker_presentation.tracker_overview

import com.vadimko.tracker_domain.model.TrackedFood
import java.time.LocalDate

sealed class TrackerOverviewEvent {
    object OnNextDayClick : TrackerOverviewEvent()
    object OnPreviousDayClick : TrackerOverviewEvent()
    data class OnDatePicker(val pickedDate: LocalDate) : TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: Meal) : TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood) : TrackerOverviewEvent()


}