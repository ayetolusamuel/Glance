package com.pedektech.glance.util

import java.net.URI
import android.util.Patterns

/**
 * Utility functions for URL manipulation and validation
 */
object UrlUtils {

    /**
     * Validates if the given string is a valid URL
     */
    fun isValidUrl(url: String): Boolean {
        val trimmedUrl = url.trim()
        return Patterns.WEB_URL.matcher(trimmedUrl).matches() &&
                trimmedUrl.startsWith("http") // Ensure it's http/https
    }

    /**
     * Checks if URL meets minimum requirements for preview and is different from current
     */
    fun isUrlEligibleForPreview(
        url: String,
        currentUrl: String? = null
    ): Boolean {
        val trimmedUrl = url.trim()
        return trimmedUrl.length > 10 &&
                isValidUrl(trimmedUrl) &&
                trimmedUrl != currentUrl // Only fetch if URL changed
    }

    /**
     * Normalize URL for comparison (remove trailing slashes, etc.)
     */
    fun normalizeUrl(url: String): String {
        return url.trim().removeSuffix("/")
    }

//
//
//    /**
//     * Validates if the given string is a valid URL
//     */
//    fun isValidUrl(url: String): Boolean {
//        return Patterns.WEB_URL.matcher(url.trim()).matches()
//    }

    /**
     * Resolves a relative URL against a base URL
     */
    fun resolveRelativeUrl(baseUrl: String, relativeUrl: String): String {
        return try {
            val baseUri = URI(baseUrl)
            baseUri.resolve(relativeUrl).toString()
        } catch (e: Exception) {
            relativeUrl // Fallback to original URL if resolution fails
        }
    }

    /**
     * Checks if URL meets minimum requirements for preview
     */
    fun isUrlEligibleForPreview(url: String): Boolean {
        val trimmedUrl = url.trim()
        return trimmedUrl.length > 10 && isValidUrl(trimmedUrl)
    }
}





