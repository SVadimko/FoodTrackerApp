package com.vadimko.onboarding_domain.usecase

import com.vadimko.core.domain.model.NutrientGoalState

class ValidateNutrients {
    operator fun invoke(state: NutrientGoalState):Result{
        val carbsRatio = state.carbsRatio.toIntOrNull()?:0
        val fatRatio = state.fatRatio.toIntOrNull()?:0
        val proteinRatio = state.proteinRatio.toIntOrNull()?:0
        val deltaVal  = carbsRatio + fatRatio + proteinRatio
        return if((deltaVal)!=100) Result.Error(deltaVal.toString())
        else Result.Success(
            carbsRatio = carbsRatio/100f,
            fatRatio = fatRatio/100f,
            proteinRatio = proteinRatio/100f,
        )
    }

    sealed class Result{
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ):Result()
        data class  Error(val message: String): Result()
    }
}