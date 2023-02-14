package com.vadimko.onboarding_presentation.nutrition

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadimko.core.util.UiEvent
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.onboarding_presentation.R
import com.vadimko.onboarding_presentation.components.ActionButton
import com.vadimko.onboarding_presentation.components.UnitTextField

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NutritionScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    viewModel: NutrientVM = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Success -> onNextClick()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "${it.msg.asString(context)} ${viewModel.sumRatio}"
                    )
                }
                else -> Unit
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.proteinRatio,
                onValueChange = { viewModel.onEvent(NutrientGoalEvent.OnProteinRatioEnter(it)) },
                unit = stringResource(
                    id = R.string.percent_proteins
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.carbsRatio,
                onValueChange = { viewModel.onEvent(NutrientGoalEvent.OnCarbRatioEnter(it)) },
                unit = stringResource(
                    id = R.string.percent_carbs
                )
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.state.fatRatio,
                onValueChange = { viewModel.onEvent(NutrientGoalEvent.OnFatRatioEnter(it)) },
                unit = stringResource(
                    id = R.string.percent_fats
                )
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {  viewModel.onEvent(NutrientGoalEvent.OnNextClick) },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}