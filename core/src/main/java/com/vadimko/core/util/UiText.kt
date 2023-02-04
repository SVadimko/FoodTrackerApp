package com.vadimko.core.util

import android.content.Context
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicString(val text: String) : UiText()
    data class StringResource(val resId: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }

   /* @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> text
            is StringResource -> stringResource(resId)
        }
    }*/
}
