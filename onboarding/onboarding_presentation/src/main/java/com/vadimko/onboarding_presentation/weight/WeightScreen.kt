package com.vadimko.onboarding_presentation.weight

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadimko.core.util.UiEvent
import com.vadimko.core_ui.*
import com.vadimko.onboarding_presentation.R
import com.vadimko.onboarding_presentation.components.ActionButton
import com.vadimko.onboarding_presentation.components.UnitTextField

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeightScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    height: String,
    viewModel: WeightVM = hiltViewModel()
) {
    viewModel.setHeightValue(height)
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Success -> onNextClick()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.msg.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = spacing.spaceLarge,
                start = spacing.spaceLarge,
                end = spacing.spaceLarge,
                top = spacing.spaceSmall
            ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_weight),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.weight,
                onValueChange = { viewModel.onWeightEnter(it) },
                unit = stringResource(
                    id = R.string.kg
                )
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { viewModel.onNextClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        if (!viewModel.bmi.isNullOrEmpty()) {
           /* val color = when{
                viewModel.bmi!!.toFloat() < 18.5f -> MediumGray
                viewModel.bmi!!.toFloat() in 18.5f..25.0f -> DarkGreen
                viewModel.bmi!!.toFloat() in 25.0f..30.0f -> CarbColor
                viewModel.bmi!!.toFloat() in 30.0f..35.0f -> Orange
                viewModel.bmi!!.toFloat() in 35.0f..40f -> FatColor
                viewModel.bmi!!.toFloat() > 40 -> MaterialTheme.colors.error
                else -> MaterialTheme.colors.onPrimary
            }*/
            Text(
                text = stringResource(id = R.string.your_bmi2, viewModel.bmi ?: "-"),
                modifier = Modifier.align(Alignment.TopStart),
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Light
            )
        }
    }

}