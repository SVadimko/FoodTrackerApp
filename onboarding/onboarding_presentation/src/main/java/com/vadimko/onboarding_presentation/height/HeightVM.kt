package com.vadimko.onboarding_presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.core.domain.usecase.FilterOutDigits
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.onboarding_domain.usecase.ValidateHeight
import com.vadimko.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HeightVM @Inject constructor(
    private val prefs: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateHeight: ValidateHeight,
) : ViewModel() {

    var height by mutableStateOf("175")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length <= 3) {
            this.height = validateHeight(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = height.toIntOrNull()
            if(heightNumber == null || heightNumber == 0){
                kotlin.run {
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_height_cant_be_empty)))
                    return@launch
                }
            }
           /* val heightNumber = height.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_height_cant_be_empty)))
                return@launch
            }*/
            prefs.saveHeight(heightNumber)
            _uiEvent.send(UiEvent.Success)
        }
    }


}
