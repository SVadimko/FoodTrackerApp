package com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vadimko.barcodesearch_presentation.R
import com.vadimko.core.util.UiText
import com.vadimko.core_ui.LocalSpacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    sheetState: BottomSheetState
) {
    val context = LocalContext.current
    val spacing = LocalSpacing.current
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .clickable {
                scope.launch {
                    sheetState.collapse()
                }
            }) {
        Spacer(modifier = Modifier.height(spacing.spaceSmall))
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(spacing.spaceExtraSmall)
                .clip(RoundedCornerShape(100.dp))
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(100.dp)
                )
        )
        Spacer(modifier = Modifier.height(spacing.spaceLarge))
        Image(
            painter = painterResource(id = R.drawable.ic_barcode),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Text(
            text = UiText.StringResource(R.string.ean_info).asString(context),
            color = Color.Black,
            modifier = Modifier.padding(spacing.spaceMedium),
            style = MaterialTheme.typography.body1
        )
    }
}