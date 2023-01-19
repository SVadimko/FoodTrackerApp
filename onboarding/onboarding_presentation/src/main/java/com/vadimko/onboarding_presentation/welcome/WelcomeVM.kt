package com.vadimko.onboarding_presentation.welcome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeVM @Inject constructor(
    private val prefs: Preferences
) : ViewModel() {

    var name by mutableStateOf("name")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onNameEnter(name: String) {
            this.name = name
    }

    fun onNextClick() {
        viewModelScope.launch {
            if(name.isEmpty() || name == "name"){
                kotlin.run {
                    _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_name)))
                    return@launch
                }
            }
            prefs.saveName(name)
            _uiEvent.send(UiEvent.Success)
        }
    }
}