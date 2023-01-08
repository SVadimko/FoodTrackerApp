package com.vadimko.onboarding_domain.di

import com.vadimko.onboarding_domain.usecase.ValidateAge
import com.vadimko.onboarding_domain.usecase.ValidateHeight
import com.vadimko.onboarding_domain.usecase.ValidateNutrients
import com.vadimko.onboarding_domain.usecase.ValidateWeight
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object OnboardingDomainModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    object OnboardingPresentation {

        @Provides
        @ViewModelScoped
        fun providesValidateNutrientsUseCase(): ValidateNutrients {
            return ValidateNutrients()
        }

        @Provides
        @ViewModelScoped
        fun providesValidateAgeUseCase(): ValidateAge {
            return ValidateAge()
        }

        @Provides
        @ViewModelScoped
        fun providesValidateHeightUseCase(): ValidateHeight {
            return ValidateHeight()
        }

        @Provides
        @ViewModelScoped
        fun providesValidateWeightUseCase(): ValidateWeight {
            return ValidateWeight()
        }
    }
}