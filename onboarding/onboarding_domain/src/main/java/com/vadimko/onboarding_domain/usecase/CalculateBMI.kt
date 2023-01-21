package com.vadimko.onboarding_domain.usecase


class CalculateBMI {
    operator fun invoke(height:String?, weight: String):String?{
        val heightFl = height?.toFloatOrNull()?.div(100)
        val weightFl = weight.toFloatOrNull()
        heightFl?.let {
            if(it != 0.0f) {
                weightFl?.let {
                    val bmi = Math.round(weightFl / (heightFl * heightFl) * 10.0) / 10.0
                    return bmi.toString()
                }
            }
        }
        return null
    }
}