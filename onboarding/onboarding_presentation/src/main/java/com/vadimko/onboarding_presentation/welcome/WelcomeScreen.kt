package com.vadimko.onboarding_presentation.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadimko.core.util.UiEvent
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.onboarding_presentation.R
import com.vadimko.onboarding_presentation.components.ActionButton
import com.vadimko.onboarding_presentation.components.UnitTextField

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WelcomeScreen(
    scaffoldState: ScaffoldState,
    onNextClick: () -> Unit,
    viewModel: WelcomeVM = hiltViewModel()
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_header),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Text(
            text = stringResource(id = R.string.welcome_description),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        UnitTextField(
            value = viewModel.name,
            onValueChange = viewModel::onNameEnter,
            unit = "",
            textStyle = TextStyle(
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 30.sp),
            keyboardType = KeyboardType.Text
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { viewModel.onNextClick() },
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}