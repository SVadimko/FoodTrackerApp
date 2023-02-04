package com.vadimko.onboarding_presentation.age

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadimko.core.domain.model.Gender
import com.vadimko.core.util.UiEvent
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.onboarding_presentation.R
import com.vadimko.onboarding_presentation.components.ActionButton
import com.vadimko.onboarding_presentation.components.SelectableButton
import com.vadimko.onboarding_presentation.components.UnitTextField
import com.vadimko.onboarding_presentation.gender.GenderScreenVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AgeScreen(
    scaffoldState: BottomSheetScaffoldState,
    onNextClick: () -> Unit,
    viewModel: AgeVM = hiltViewModel()
) {
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
            .padding(spacing.spaceLarge),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.whats_your_age),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
         UnitTextField(value = viewModel.age, onValueChange = viewModel::onAgeEnter, unit = stringResource(
             id = R.string.years
         ))
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { viewModel.onNextClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}