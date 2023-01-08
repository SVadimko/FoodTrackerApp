package com.vadimko.tracker_data.repository

import android.util.Log
import com.vadimko.tracker_data.local.TrackerDao
import com.vadimko.tracker_data.mapper.toTrackableFood
import com.vadimko.tracker_data.mapper.toTrackedFood
import com.vadimko.tracker_data.mapper.toTrackedFoodEntity
import com.vadimko.tracker_data.remote.OpenFoodApi
import com.vadimko.tracker_domain.model.TrackableFood
import com.vadimko.tracker_domain.model.TrackedFood
import com.vadimko.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val openFoodApi: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = openFoodApi.searchResponse(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(searchDto.products.mapNotNull { it.toTrackableFood() })
        } catch (e: Exception) {
            Log.wtf("TF_searchFood", e.message)
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodForDay(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities ->
            entities.map { it.toTrackedFood() }
        }
    }
}

