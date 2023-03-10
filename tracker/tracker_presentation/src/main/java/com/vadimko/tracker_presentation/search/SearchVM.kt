package com.vadimko.tracker_presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimko.core.domain.usecase.FilterOutDigits
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.tracker_domain.use_case.TrackerUseCase
import com.vadimko.tracker_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val trackerUseCase: TrackerUseCase,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var isMakingRequest = false

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearch -> {
                executeSearch()
            }
            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = state.query.isBlank() && !event.isFocused
                )
            }
            is SearchEvent.OnAmountFoodForChange -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food) {
                            it.copy(amount = filterOutDigits(event.amount))
                        } else it
                    }
                )
            }
            is SearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query, page = 1, trackableFood = emptyList())
            }
            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFood = state.trackableFood.map {
                        if (it.food == event.food) {
                            it.copy(isExpended = !it.isExpended)
                        } else it
                    }
                )
            }
            is SearchEvent.OnInfoTrackFoodClick -> {
                state = state.copy(isShowInfo = true, infoFood = event.food)
            }
            is SearchEvent.OnInfoClose -> {
                state = state.copy(isShowInfo = false)
            }
        }
    }

    private fun executeSearch() {
        if (isMakingRequest) {
            return
        }
        Log.wtf("make request", state.query + "  " + state.page)
        isMakingRequest = true
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,

                )
            trackerUseCase
                .searchFood(state.query, page = state.page)
                .onSuccess { foods ->
                    state =
                        state.copy(
                            trackableFood = state.trackableFood + foods.map {
                                TrackableFoodUiState(
                                    food = it
                                )
                            },
                            isSearching = false,
                            page = state.page + 1,
                            endReached = foods.isEmpty()
                        )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UiEvent.ShowSnackBar(
                            UiText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
            isMakingRequest = false
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFood.find { it.food == event.food }
            trackerUseCase.trackFood(
                food = uiState?.food ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    //need move to useCase
    fun prepareList(state: SearchState): List<AdditionalNutrientsWithText> {
        val some = state.infoFood?.additionalNutriments
        val list: MutableList<AdditionalNutrientsWithText> = mutableListOf()
        some?.let { item ->
            item.alcohol?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.alcohol),
                        it.toString()
                    )
                )
            }
            item.calcium?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.calcium),
                        it.toString()
                    )
                )
            }
            item.cellulose?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.cellulose),
                        it.toString()
                    )
                )
            }
            item.cu?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.cu),
                        it.toString()
                    )
                )
            }
            item.iron?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.iron),
                        it.toString()
                    )
                )
            }
            item.fiber?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.fiber),
                        it.toString()
                    )
                )
            }
            item.magnesium?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.magnesium),
                        it.toString()
                    )
                )
            }
            item.phosphorus?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.phosphorus),
                        it.toString()
                    )
                )
            }
            item.potassium?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.potassium),
                        it.toString()
                    )
                )
            }
            item.omega3?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.omega3),
                        it.toString()
                    )
                )
            }
            item.omega6?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.omega6),
                        it.toString()
                    )
                )
            }
            item.salt?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.salt),
                        it.toString()
                    )
                )
            }
            item.saturatedFat?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.saturatedFat),
                        it.toString()
                    )
                )
            }
            item.sodium?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.sodium),
                        it.toString()
                    )
                )
            }
            item.sugars?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.sugars),
                        it.toString()
                    )
                )
            }
            item.vitaminC?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.vitaminC),
                        it.toString()
                    )
                )
            }
            item.zinc?.let {
                list.add(
                    AdditionalNutrientsWithText(
                        UiText.StringResource(R.string.zinc),
                        it.toString()
                    )
                )
            }

        }
        return list
    }
}