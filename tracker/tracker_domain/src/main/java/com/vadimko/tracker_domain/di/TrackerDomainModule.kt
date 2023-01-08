package com.vadimko.tracker_domain.di

import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.tracker_domain.repository.TrackerRepository
import com.vadimko.tracker_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCase {
        return TrackerUseCase(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodForDate = GetFoodForDate(repository),
            deleteFood = DeleteFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
    }
}