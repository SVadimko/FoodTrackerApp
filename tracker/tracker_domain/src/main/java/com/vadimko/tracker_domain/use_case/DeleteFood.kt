package com.vadimko.tracker_domain.use_case

import com.vadimko.tracker_domain.model.TrackedFood
import com.vadimko.tracker_domain.repository.TrackerRepository

class DeleteFood(
    private val repo: TrackerRepository
) {
    suspend operator fun invoke(food: TrackedFood){
        repo.deleteTrackedFood(food = food)
    }
}