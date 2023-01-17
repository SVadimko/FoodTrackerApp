package com.vadimko.onboarding_presentation.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.vadimko.core.navigation.Route
import com.vadimko.core.util.UiEvent
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.onboarding_presentation.R
import com.vadimko.onboarding_presentation.components.ActionButton


@Composable
@Deprecated("Use WelcomeScreen instead")
fun WelcomeScreen(onNavigate:(UiEvent.Navigate) -> Unit) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.spaceMedium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.welcome_text),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = { onNavigate(UiEvent.Navigate(Route.GENDER)) },
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}