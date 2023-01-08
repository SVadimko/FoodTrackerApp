package com.vadimko.tracker_domain.use_case

import com.vadimko.tracker_domain.model.TrackableFood
import com.vadimko.tracker_domain.repository.TrackerRepository

class SearchFood(
    private val repo: TrackerRepository
) {
    suspend operator fun invoke(
        searchString: String,
        page: Int = 1,
        pageSize: Int = 100
    ): Result<List<TrackableFood>> {
        if(searchString.isBlank()) {
            return Result.success(emptyList())
        }
        return repo.searchFood(searchString.trim(), page, pageSize)
    }
}