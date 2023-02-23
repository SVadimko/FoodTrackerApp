package com.vadimko.onboarding_presentation.gender

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vadimko.core.util.UiEvent
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.onboarding_presentation.R
import com.vadimko.onboarding_presentation.components.ActionButton
import com.vadimko.onboarding_presentation.components.GenderPicker

@Composable
fun GenderScreen(
    onNextClick: () -> Unit,
    viewModel: GenderScreenVM = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    //  val gender = viewModel.selectedGender
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
                text = stringResource(id = R.string.whats_your_gender),
                style = MaterialTheme.typography.h3
            )
            // Spacer(modifier = Modifier.height(spacing.spaceMedium))
            GenderPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                selectedGenderFromVM = viewModel.selectedGender,
                onGenderSelected = {
                    viewModel.onGenderClick(it)
                })
            /*    Row {
                    SelectableButton(
                        text = stringResource(id = R.string.male),
                        isSelected = viewModel.selectedGender is Gender.Male,
                        color = MaterialTheme.colors.primaryVariant,
                        selectedTextColor = Color.White,
                        onClick = {
                            viewModel.onGenderClick(Gender.Male) },
                        textStyle = MaterialTheme.typography.button.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceMedium) )
                    SelectableButton(
                        text = stringResource(id = R.string.female),
                        isSelected = viewModel.selectedGender is Gender.Female,
                        color = MaterialTheme.colors.primaryVariant,
                        selectedTextColor = Color.White,
                        onClick = {
                            viewModel.onGenderClick(Gender.Female) },
                        textStyle = MaterialTheme.typography.button.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                }*/
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { viewModel.onNextClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }

}