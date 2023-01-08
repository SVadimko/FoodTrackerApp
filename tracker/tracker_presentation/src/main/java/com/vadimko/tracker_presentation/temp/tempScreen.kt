package com.vadimko.tracker_presentation.temp

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadimko.core.util.UiEvent

@Composable
fun TempScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TempVM = hiltViewModel()
){
    viewModel.getConfig()
}