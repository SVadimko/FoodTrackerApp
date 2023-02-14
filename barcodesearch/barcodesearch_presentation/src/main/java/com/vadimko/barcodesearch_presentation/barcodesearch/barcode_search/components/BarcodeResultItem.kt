package com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vadimko.core_ui.LocalSpacing

@Composable
fun BarcodeResultItem(
    param: String,
    value: String
){
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
      Text(text = param, style = MaterialTheme.typography.body1, modifier = Modifier.weight(1f))
      Spacer(modifier = Modifier.width(spacing.spaceMedium))
      Text(text = value,style = MaterialTheme.typography.body1)
    }

}