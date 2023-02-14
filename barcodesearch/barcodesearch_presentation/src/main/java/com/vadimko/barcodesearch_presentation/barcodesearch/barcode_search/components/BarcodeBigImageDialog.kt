package com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.vadimko.core_ui.LocalSpacing

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BarcodeBigImageDialog(
    url:String?,
   // dialogState:MutableState<Boolean>,
    contentScale: ContentScale,
    onClick: (Boolean)->Unit
){
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Dialog(
        onDismissRequest = { //dialogState.value = false
                           onClick(false)},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
    ) {
        Box(
            modifier = Modifier
            //    .fillMaxSize()
                .clip(RoundedCornerShape(spacing.spaceSmall))
                .background(Color.Transparent)
                .padding(bottom = spacing.spaceSmall)
//                .clickable { dialogState.value = false },
                .clickable { onClick(false) },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberImagePainter(
                    data = url,
                    builder = {
                        crossfade(true)
                        //error(R.drawable.ic_burger)
                        //fallback(R.drawable.ic_burger)
                    }
                ),
                contentDescription = null,
                contentScale = contentScale,
                modifier = Modifier
                    .fillMaxSize()
//                    .clickable { dialogState.value = false }
                    .clickable { onClick(false) }
                    .clip(RoundedCornerShape(10.dp)),
                alignment = Alignment.Center
            )
        }
    }
}