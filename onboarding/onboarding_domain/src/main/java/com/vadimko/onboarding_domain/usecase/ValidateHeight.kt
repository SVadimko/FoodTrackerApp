package com.vadimko.onboarding_domain.usecase

import com.vadimko.onboarding_domain.support.MAX_HEIGHT
import com.vadimko.onboarding_domain.support.MIN_HEIGHT

class ValidateHeight {
    operator fun invoke(text:String):String{
        var textRes= text.filter { it.isDigit() }
        textRes.toIntOrNull()?.let {
            if(it > MAX_HEIGHT.toInt()) textRes = MAX_HEIGHT
        }
        return textRes
    }
}