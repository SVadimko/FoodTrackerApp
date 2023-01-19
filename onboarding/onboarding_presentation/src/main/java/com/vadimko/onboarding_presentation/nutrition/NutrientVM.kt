package com.vadimko.onboarding_presentation.nutrition

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimko.core.domain.model.NutrientGoalState
import com.vadimko.core.domain.preferences.Preferences
import com.vadimko.core.domain.usecase.FilterOutDigits
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.onboarding_domain.usecase.ValidateNutrients
import com.vadimko.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NutrientVM @Inject constructor(
    private val prefs: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val nutrients: ValidateNutrients,
) : ViewModel() {

    var sumRatio by mutableStateOf("0")
        private set

    var state by mutableStateOf(NutrientGoalState())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbRatioEnter -> {
                state = state.copy(carbsRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.OnProteinRatioEnter -> {
                state = state.copy(proteinRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.OnFatRatioEnter -> {
                state = state.copy(fatRatio = filterOutDigits(event.ratio))
            }
            is NutrientGoalEvent.OnNextClick -> {
                val result = nutrients(
                    NutrientGoalState(
                        carbsRatio = state.carbsRatio,
                        proteinRatio = state.proteinRatio,
                        fatRatio = state.fatRatio
                    )
                )
                when (result) {
                    is ValidateNutrients.Result.Error -> {
                        sumRatio = result.message
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_not_100_percent)))
                            return@launch
                        }
                    }
                    is ValidateNutrients.Result.Success -> {
                        prefs.saveCarbRatio(result.carbsRatio)
                        prefs.saveFatRatio(result.fatRatio)
                        prefs.saveProteinRatio(result.proteinRatio)
                        Log.wtf(
                            "nutrients",
                            "${result.carbsRatio}  ${result.fatRatio}  ${result.proteinRatio}"
                        )
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Success)
                        }
                    }
                }
            }
        }
    }
}



