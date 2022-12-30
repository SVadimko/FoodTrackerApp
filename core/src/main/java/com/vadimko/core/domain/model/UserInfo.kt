package com.vadimko.core.domain.model

data class UserInfo(
    val gender: Gender,
    var age: Int,
    var weight: Float,
    var height: Int,
    var activityLevel: ActivityLevel,
    var goalType: GoalType,
    val carbRatio: Float,
    val proteinRatio: Float,
    val fatRatio: Float
)
