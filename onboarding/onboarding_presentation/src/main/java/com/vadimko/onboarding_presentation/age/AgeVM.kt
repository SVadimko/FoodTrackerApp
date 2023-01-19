package com.vadimko.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.core.domain.usecase.FilterOutDigits
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.onboarding_domain.usecase.ValidateAge
import com.vadimko.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeVM @Inject constructor(
    private val prefs: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val validateAge: ValidateAge,
) : ViewModel() {

    var age by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if (age.length <= 3) {
            //this.age = filterOutDigits(age)
            this.age = validateAge(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_age_cant_be_empty)))
                return@launch
            }
            prefs.saveAge(ageNumber)
            _uiEvent.send(UiEvent.Success)
        }
    }
}