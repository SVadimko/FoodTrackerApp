package com.vadimko.onboarding_domain.usecase

import com.vadimko.onboarding_domain.support.MAX_HEIGHT
import com.vadimko.onboarding_domain.support.MAX_WEIGHT

class ValidateWeight {
    operator fun invoke(text:String):String{
        var textRes= text.replace(',', '.')
        textRes.toFloatOrNull()?.let {
            if(it > MAX_WEIGHT.toFloat()) textRes = MAX_WEIGHT
        }
        return textRes
    }
}