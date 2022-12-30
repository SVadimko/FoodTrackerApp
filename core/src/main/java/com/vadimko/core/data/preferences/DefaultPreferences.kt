package com.vadimko.core.data.preferences

import android.content.SharedPreferences
import com.vadimko.core.domain.model.ActivityLevel
import com.vadimko.core.domain.model.Gender
import com.vadimko.core.domain.model.GoalType
import com.vadimko.core.domain.model.UserInfo
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_ACTIVITY
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_AGE
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_CARB_RATIO
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_FAT_RATIO
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_GENDER
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_GOAL
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_HEIGHT
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_PROTEIN_RATIO
import com.vadimko.core.domain.preferences.Preferences.Companion.KEY_WEIGHT

class DefaultPreferences(
    private val sharedPrefs: SharedPreferences
) : Preferences {
    override fun saveGender(gender: Gender) {
       sharedPrefs.edit()
               .putString(KEY_GENDER, gender.name)
               .apply()
    }

    override fun saveAge(age: Int) {
        sharedPrefs.edit()
            .putInt(KEY_AGE, age)
            .apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPrefs.edit()
            .putFloat(KEY_WEIGHT, weight)
            .apply()
    }

    override fun saveHeight(height: Int) {
        sharedPrefs.edit()
            .putInt(KEY_HEIGHT, height)
            .apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPrefs.edit()
            .putString(KEY_ACTIVITY, level.name)
            .apply()
    }

    override fun saveGoalType(goalType: GoalType) {
        sharedPrefs.edit()
            .putString(KEY_GOAL, goalType.name)
            .apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPrefs.edit()
            .putFloat(KEY_CARB_RATIO, ratio)
            .apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPrefs.edit()
            .putFloat(KEY_PROTEIN_RATIO, ratio)
            .apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPrefs.edit()
            .putFloat(KEY_FAT_RATIO, ratio)
            .apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age = sharedPrefs.getInt(KEY_AGE, 20)
        val gender = Gender.fromString(sharedPrefs.getString(KEY_GENDER, Gender.Male.name)?:Gender.Male.name)
        val height = sharedPrefs.getInt(KEY_HEIGHT, 175)
        val weight = sharedPrefs.getFloat(KEY_WEIGHT, 70F)
        val activityLevel = ActivityLevel.fromString(sharedPrefs.getString(KEY_ACTIVITY, ActivityLevel.Medium.name)?:ActivityLevel.Medium.name)
        val goalType = GoalType.fromString(sharedPrefs.getString(KEY_GOAL, GoalType.KeepWeight.name)?:GoalType.KeepWeight.name)
        val carbRatio = sharedPrefs.getFloat(KEY_CARB_RATIO, 45F)
        val proteinRatio = sharedPrefs.getFloat(KEY_PROTEIN_RATIO, 45F)
        val fatRatio = sharedPrefs.getFloat(KEY_FAT_RATIO, 10F)
        return UserInfo(
            gender = gender,
            age = age,
            height = height,
            weight = weight,
            activityLevel = activityLevel,
            goalType = goalType,
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio

        )

    }



}