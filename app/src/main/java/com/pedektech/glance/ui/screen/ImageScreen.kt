package com.pedektech.glance.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pedektech.glance.ui.components.*
import com.pedektech.glance.ui.theme.AppColors

@Composable
fun ImageScreen(
    viewModel: ImageScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ScreenContent(
        inputText = uiState.inputText,
        onInputChange = viewModel::onInputTextChanged,
        isSharePressed = uiState.isSharePressed,
        onSharePressedChange = viewModel::onSharePressedChanged,
        onShareClick = viewModel::onShareClicked,
        previewData = uiState.previewData,
        isLoading = uiState.isLoading,
        isFetching = uiState.isFetching
    )
}

@Composable
private fun ScreenContent(
    inputText: String,
    onInputChange: (String) -> Unit,
    isSharePressed: Boolean,
    onSharePressedChange: (Boolean) -> Unit,
    onShareClick: () -> Unit,
    previewData: com.pedektech.glance.data.model.LinkPreviewData?,
    isLoading: Boolean,
    isFetching: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        LinkInputField(
            value = inputText,
            onValueChange = onInputChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        when {
            isLoading -> LoadingIndicator()
            previewData != null -> {
                TechCrunchPreviewCard(
                    modifier = Modifier.fillMaxWidth(),
                    previewData = previewData
                )
                // Show loading overlay if fetching in background
                if (isFetching) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            else -> {
                // Show empty state or instructions
                PlaceholderPreview()
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ShareActionButton(
            isPressed = isFetching || isSharePressed, // Disable during fetch
            onPressedChange = onSharePressedChange,
            onClick = onShareClick
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun PlaceholderPreview() {
    TechCrunchPreviewCard(
        modifier = Modifier.fillMaxWidth(),
        previewData = null
    )
}