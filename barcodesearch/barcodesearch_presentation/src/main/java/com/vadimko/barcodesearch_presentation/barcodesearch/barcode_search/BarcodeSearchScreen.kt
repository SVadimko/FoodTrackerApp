package com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.vadimko.barcodesearch_presentation.R
import com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.components.BarcodeBigImageDialog
import com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.components.BottomSheet
import com.vadimko.barcodesearch_presentation.barcodesearch.barcode_search.components.ShimmerListItem
import com.vadimko.core.util.UiEvent
import com.vadimko.core.util.UiText
import com.vadimko.core_ui.LocalSpacing
import com.vadimko.core_ui.components.SearchTextField
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class, ExperimentalCoilApi::class)
@Composable
fun BarcodeSearchScreen(
    viewModel: BarcodeSearchVM = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state = viewModel.state
    val dialogState = remember {
        mutableStateOf(false)
    }
    val dialogUrl = remember {
        mutableStateOf("")
    }
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.msg.asString(context)
                    )
                    keyboardController?.hide()
                }
                is UiEvent.NavigateUp -> {
                    //  onNavigateUp()
                }
                else -> Unit
            }
        }
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheet(
                sheetState = sheetState
            )
        },
        modifier = Modifier.fillMaxSize(),
        sheetGesturesEnabled = true,
        sheetPeekHeight = spacing.default
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = spacing.spaceMedium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.barcodeSearchTitle),
                    style = MaterialTheme.typography.h2,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        scope.launch {
                            if (sheetState.isCollapsed) {
                                keyboardController?.hide()
                                sheetState.expand()
                            } else {
                                sheetState.collapse()
                            }
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.LightGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            SearchTextField(
                text = state.query,
                onValueChange = {
                    viewModel.onEvent(BarcodeSearchEvent.OnQueryChange(it))
                },
                shouldShowHint = state.isHintVisible,
                onSearch = {
                    keyboardController?.hide()
                    viewModel.onEvent(BarcodeSearchEvent.OnSearch)
                },
                onFocusChanged = {
                    if (it.isFocused) {
                        scope.launch {
                            sheetState.collapse()
                        }
                    }
                    viewModel.onEvent(BarcodeSearchEvent.OnSearchFocusChange(it.isFocused))
                },
                hint = UiText.StringResource(R.string.search).asString(context)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            if (!state.resultList.url.isNullOrEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 0..state.resultList.url.lastIndex) {
                        Image(
                            painter = rememberImagePainter(
                                data = state.resultList.url.getOrNull(i),
                                builder = {
                                    crossfade(true)
                                    error(R.drawable.ic_no_image)
                                    fallback(R.drawable.ic_no_image)
                                }
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(spacing.spaceSmall)
                                .clip(RoundedCornerShape(spacing.spaceSmall))
                                .clickable {
                                    dialogUrl.value = state.resultList.url
                                        .getOrNull(i)
                                        .toString()
                                    dialogState.value = true
                                },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
            }
            if (!state.resultList.resultList.isNullOrEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(state.resultList.resultList) { item ->
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(text = item.param, maxLines = 1, softWrap = false)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = item.value,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.End
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .background(color = MaterialTheme.colors.onSurface)
                                    .padding(horizontal = spacing.spaceSmall),
                                thickness = 1.dp
                            )
                        }

                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isSearching -> ShimmerListItem(isLoading = state.isSearching)
                    state.error == true -> {
                        Text(
                            text = stringResource(id = R.string.no_results),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        if (dialogState.value) {
            BarcodeBigImageDialog(
                url = dialogUrl.value,
                contentScale = ContentScale.Fit,
                onClick = {
                    dialogState.value = it
                })
        }
    }
}


