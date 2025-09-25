package com.pedektech.glance.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedektech.glance.data.model.LinkPreviewData
import com.pedektech.glance.data.repository.LinkPreviewRepository
import com.pedektech.glance.util.UrlUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ImageScreenState(
    val inputText: String = "",
    val isSharePressed: Boolean = false,
    val previewData: LinkPreviewData? = null,
    val isLoading: Boolean = false,
    val isFetching: Boolean = false // Add this to track ongoing requests
)

@HiltViewModel
class ImageScreenViewModel @Inject constructor(
    private val repository: LinkPreviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ImageScreenState())
    val uiState: StateFlow<ImageScreenState> = _uiState.asStateFlow()

    private var currentFetchJob: Job? = null

    init {
        setupUrlDebouncing()
    }

    fun onInputTextChanged(text: String) {
        _uiState.update { it.copy(inputText = text) }
    }

    fun onSharePressedChanged(isPressed: Boolean) {
        _uiState.update { it.copy(isSharePressed = isPressed) }
    }

    fun onShareClicked() {
        // Handle share functionality
    }

    private fun setupUrlDebouncing() {
        _uiState
            .map { it.inputText }
            .filter { newUrl ->
                val currentPreviewUrl = _uiState.value.previewData?.url
                UrlUtils.isUrlEligibleForPreview(newUrl, currentPreviewUrl) &&
                        !_uiState.value.isFetching
            }
            .distinctUntilChanged() // Only emit if URL actually changed
            .debounce(750L)
            .onEach { url ->
                fetchLinkPreview(url)
            }
            .launchIn(viewModelScope)
    }


    private fun fetchLinkPreview(url: String) {
        // Cancel previous fetch job if it's still running
        currentFetchJob?.cancel()

        currentFetchJob = viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    isFetching = true, // Set fetching flag
                    previewData = null
                )
            }

            try {
                val previewData = repository.getLinkPreview(url)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isFetching = false, // Reset fetching flag
                        previewData = previewData
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isFetching = false, // Reset even on error
                        previewData = null
                    )
                }
            }
        }
    }

    // Optional: Cancel ongoing fetch when needed
    fun cancelCurrentFetch() {
        currentFetchJob?.cancel()
        _uiState.update { it.copy(isLoading = false, isFetching = false) }
    }
}