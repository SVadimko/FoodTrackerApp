package com.vadimko.core.domain.usecase

import android.util.Log

class FilterOutFloatDigits {
    operator fun invoke(text:String):String{
//        return text.filter { it.isDigit() || it == ',' }
        return text.replace(',', '.')
    }
}