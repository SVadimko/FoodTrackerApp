package com.vadimko.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.core.domain.usecase.FilterOutFloatDigits
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.onboarding_domain.usecase.CalculateBMI
import com.vadimko.onboarding_domain.usecase.ValidateWeight
import com.vadimko.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightVM @Inject constructor(
    private val prefs: Preferences,
    private val filterOutFloatDigits: FilterOutFloatDigits,
    private val validateWeight: ValidateWeight,
    private val calculateBMI: CalculateBMI
) : ViewModel() {

    var weight by mutableStateOf("70.0")
        private set

    var height: String? = null

    var bmi by mutableStateOf(calculateBMI(height,weight))
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setHeightValue(heightVal: String){
        height = heightVal
        calculateBMI(height, weight)?.let {
            bmi = it
        }
    }

    fun onWeightEnter(weight: String) {
        if (weight.length <= 5) {
            this.weight = validateWeight(weight)
            calculateBMI(height, weight)?.let {
                bmi = it
            }
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: kotlin.run {
                _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_weight_cant_be_empty)))
                return@launch
            }
            prefs.saveWeight(weightNumber)
            _uiEvent.send(UiEvent.Success)
        }
    }

}