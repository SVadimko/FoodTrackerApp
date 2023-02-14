package com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadimko.barcodesearch_domain.model.BarcodeState
import com.vadimko.barcodesearch_domain.use_case.BarcodeSearch
import com.vadimko.barcodesearch_domain.use_case.GetListsFromResult
import com.vadimko.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BarcodeSearchVM @Inject constructor(
    private val usecase: BarcodeSearch,
    private val resToList: GetListsFromResult
) : ViewModel(

) {
    var state by mutableStateOf(BarcodeSearchState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: BarcodeSearchEvent) {
        when (event) {
            is BarcodeSearchEvent.OnSearch -> {
                executeSearch()
            }
            is BarcodeSearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = state.query.isBlank() && !event.isFocused
                )
            }
            is BarcodeSearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
            }
        }
    }

    private fun executeSearch() {
        Log.wtf("make request", state.query)
        viewModelScope.launch {
            state = state.copy(
                isSearching = true, error = false
            )
            val search = if(state.query.isNullOrEmpty()) "8690627023401" else state.query
            usecase.invoke(search)
                .onSuccess {
                    state = state.copy(resultList = resToList.invoke(it), isSearching = false, error = false)
                }
                .onFailure {
                    state = state.copy(isSearching = false, error = true, resultList = BarcodeState(
                        emptyList(), emptyList()))
                }
        }
    }
}