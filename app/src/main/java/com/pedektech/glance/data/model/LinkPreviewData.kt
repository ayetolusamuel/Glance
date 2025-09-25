package com.pedektech.glance.data.model

/**
 * Represents metadata extracted from a URL for preview purposes
 * @param title The title of the web page
 * @param description The description of the web page
 * @param imageUrl The URL of the preview image
 * @param url The original URL
 * @param message Optional message indicating status or errors
 */
data class LinkPreviewData(
    val title: String,
    val description: String,
    val imageUrl: String,
    val url: String,
    val message: String? = null
) {
    companion object {
        val EMPTY = LinkPreviewData(
            title = "",
            description = "",
            imageUrl = "",
            url = "",
            message = null
        )
    }

    val isValid: Boolean
        get() = title.isNotBlank() || description.isNotBlank() || imageUrl.isNotBlank()
}