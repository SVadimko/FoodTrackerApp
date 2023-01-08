package com.vadimko.tracker_domain.use_case

import com.vadimko.tracker_domain.model.TrackedFood
import com.vadimko.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodForDate(
    private val repo: TrackerRepository
) {
    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repo.getFoodsForDate(date)
    }
}