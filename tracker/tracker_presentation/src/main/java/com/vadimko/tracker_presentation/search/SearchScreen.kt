package com.vadimko.tracker_presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.tracker_domain.model.MealType
import com.vadimko.tracker_presentation.R
import com.vadimko.tracker_presentation.search.components.SearchTextField
import com.vadimko.tracker_presentation.search.components.TrackableFoodItem
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
    scaffoldState: BottomSheetScaffoldState,
    mealName: String,
    dayOfMonth: Int,
    month: Int,
    year: Int,
    onNavigateUp: () -> Unit,
    viewModel: SearchVM = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg.asString(context)
                    )
                    keyboardController?.hide()
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                else -> Unit
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Text(
            text = stringResource(id = R.string.add_meal, mealName),
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        SearchTextField(
            text = state.query,
            onValueChange = {
                viewModel.onEvent(SearchEvent.OnQueryChange(it))
            },
            shouldShowHint = state.isHintVisible,
            onSearch = {
                keyboardController?.hide()
                viewModel.onEvent(SearchEvent.OnSearch(viewModel.state.page))
            },
            onFocusChanged = {
                viewModel.onEvent(SearchEvent.OnSearchFocusChange(it.isFocused))
            })
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.trackableFood.size) { i ->
                val food = state.trackableFood[i]
                if (i >= state.trackableFood.size - 1 && !state.isSearching && !state.endReached) {
                    viewModel.onEvent(SearchEvent.OnSearch(viewModel.state.page))
                }
//            }
                TrackableFoodItem(
                    trackableFoodUiState = food,
                    onClick = {
                        viewModel.onEvent(SearchEvent.OnToggleTrackableFood(food.food))
                    },
                    onAmountChange = {
                        viewModel.onEvent(
                            SearchEvent.OnAmountFoodForChange(
                                food = food.food,
                                amount = it
                            )
                        )
                    },
                    onTrack = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnTrackFoodClick(
                                food = food.food,
                                mealType = MealType.fromString(mealName),
                                date = LocalDate.of(year, month, dayOfMonth)
                            )
                        )
                    },
                    onInfo = {
                        keyboardController?.hide()
                        viewModel.onEvent(
                            SearchEvent.OnInfoTrackFoodClick(
                                food = food.food,
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSearching -> CircularProgressIndicator()
            state.trackableFood.isEmpty() -> {
                Text(
                    text = stringResource(id = R.string.no_results),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
    if (state.isShowInfo) {
        Dialog(
            onDismissRequest = {  },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(spacing.spaceSmall))
                    .background(MaterialTheme.colors.surface)
                    .padding(bottom = spacing.spaceSmall)
                    .clickable { viewModel.onEvent(SearchEvent.OnInfoClose) },
                contentAlignment = Alignment.Center
            ) {
                LazyColumn() {
                    item {
                        Text(
                            text = UiText.StringResource(R.string.additional_info).asString(context),
                            style = MaterialTheme.typography.h2,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.primary)
                                .padding(spacing.spaceSmall)
                        )
                    }
                    items(prepareList(state)) { item ->
                        Column {
                            Spacer(modifier = Modifier.height(spacing.spaceSmall))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = spacing.spaceSmall)
                            ) {
                                Text(text = item.name.asString(context))
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = "${item.value} ${UiText.StringResource(R.string.grams).asString(context)}")
                            }
                            Divider(
                                modifier = Modifier
                                    .background(color = MaterialTheme.colors.onSurface)
                                    .padding(horizontal = spacing.spaceSmall),
                                thickness = 1.dp
                            )
                        }

                    }
                }
            }
        }
    }
}

private fun prepareList(state: SearchState): List<AdditionalNutrientsWithText> {
    var some = state.infoFood?.additionalNutriments
    var list: MutableList<AdditionalNutrientsWithText> = mutableListOf()
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
            list.add(AdditionalNutrientsWithText(UiText.StringResource(R.string.cu), it.toString()))
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
