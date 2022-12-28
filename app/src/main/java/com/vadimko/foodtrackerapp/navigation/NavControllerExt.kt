package com.vadimko.foodtrackerapp.navigation

import androidx.navigation.NavController
import com.vadimko.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}