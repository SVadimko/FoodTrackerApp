package com.vadimko.onboarding_presentation.height

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
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
fun HeightScreen(
    scaffoldState: ScaffoldState,
    //onNextClick: () -> Unit,
    onNextClick: (String) -> Unit,
    viewModel: HeightVM = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Success -> onNextClick(viewModel.height)
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
                text = stringResource(id = R.string.whats_your_height),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            UnitTextField(
                value = viewModel.height,
                onValueChange = viewModel::onHeightEnter,
                unit = stringResource(
                    id = R.string.cm
                )
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { viewModel.onNextClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}