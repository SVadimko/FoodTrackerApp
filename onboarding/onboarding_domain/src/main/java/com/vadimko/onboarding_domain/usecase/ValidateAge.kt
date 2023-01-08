package com.vadimko.onboarding_domain.usecase

import com.vadimko.onboarding_domain.support.MAX_AGE

class ValidateAge {
    operator fun invoke(text:String):String{
        var textRes= text.filter { it.isDigit() }
        textRes.toIntOrNull()?.let {
            if(it > MAX_AGE.toInt()) textRes = MAX_AGE
        }
        return textRes
    }
}