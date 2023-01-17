package com.vadimko.core.domain.preferences

import com.vadimko.core.domain.model.ActivityLevel
import com.vadimko.core.domain.model.Gender
import com.vadimko.core.domain.model.GoalType
import com.vadimko.core.domain.model.UserInfo

interface Preferences {

    fun saveName(name: String)
    fun saveGender(gender: Gender)
    fun saveAge(age: Int)
    fun saveWeight(weight: Float)
    fun saveHeight(height: Int)
    fun saveActivityLevel(level: ActivityLevel)
    fun saveGoalType(goalType: GoalType)
    fun saveCarbRatio(ratio: Float)
    fun saveProteinRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)

    fun loadUserInfo():UserInfo
    fun saveShouldShowOnboarding(shouldShow: Boolean)
    fun loadShouldShowOnboarding():Boolean
    fun getUserName():String

    companion object{
        const val KEY_NAME = "name"
        const val KEY_GENDER = "gender"
        const val KEY_AGE = "age"
        const val KEY_WEIGHT = "weight"
        const val KEY_HEIGHT = "height"
        const val KEY_ACTIVITY = "activity_level"
        const val KEY_GOAL = "goal"
        const val KEY_CARB_RATIO = "carb"
        const val KEY_FAT_RATIO = "fat"
        const val KEY_PROTEIN_RATIO = "protein"
        const val KEY_SHOULD_SHOW_ONBOARDING = "should_show_onboarding"
    }

}