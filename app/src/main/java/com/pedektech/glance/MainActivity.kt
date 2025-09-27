package com.pedektech.glance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.pedektech.glance.ui.screen.ImageScreen
import com.pedektech.glance.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // State to hold validated URLs only
    private var sharedUrlState by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Handle the intent that launched the activity
        handleIntent(intent)

        setContent {
            AppTheme {
                ImageScreen(sharedUrl = sharedUrlState)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action != Intent.ACTION_SEND || intent.type != "text/plain") {
            Log.d("MainActivity", "Intent not supported: action=${intent?.action}, type=${intent?.type}")
            return
        }

        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        Log.d("ShareSheet", "Received shared text: $sharedText")

        if (sharedText.isNullOrBlank()) {
            Log.w("ShareSheet", "Shared text is null or empty")
            return
        }

        val validatedUrl = extractAndValidateUrl(sharedText)
        if (validatedUrl != null) {
            sharedUrlState = validatedUrl
            Log.d("ShareSheet", "Valid URL accepted: $validatedUrl")
        } else {
            Log.w("ShareSheet", "No valid URL found in shared text")
            // Optionally show a toast or snackbar to inform user
        }
    }

    /**
     * Extracts and validates URLs from shared text using best practices
     * @param text The shared text that may contain a URL
     * @return Valid URL string or null if no valid URL found
     */
    private fun extractAndValidateUrl(text: String): String? {
        // First, try to find URLs in the text using Android's built-in pattern matcher
        val matcher = Patterns.WEB_URL.matcher(text)
        val urls = mutableListOf<String>()

        while (matcher.find()) {
            val url = matcher.group()
            if (url != null && isValidUrl(url)) {
                urls.add(url)
            }
        }

        return when {
            urls.isNotEmpty() -> {
                // Return the first valid URL found
                val url = urls.first()
                // Ensure URL has proper scheme
                when {
                    url.startsWith("https://", ignoreCase = true) -> url
                    url.startsWith("http://", ignoreCase = true) -> url
                    else -> "https://$url" // Default to HTTPS for security
                }
            }
            // Fallback: check if the entire text is a URL
            isValidUrl(text.trim()) -> {
                val trimmedText = text.trim()
                when {
                    trimmedText.startsWith("https://", ignoreCase = true) -> trimmedText
                    trimmedText.startsWith("http://", ignoreCase = true) -> trimmedText
                    else -> "https://$trimmedText"
                }
            }
            else -> null
        }
    }

    /**
     * Validates if a string is a proper URL using best practices
     * @param urlString The string to validate
     * @return true if valid URL, false otherwise
     */
    private fun isValidUrl(urlString: String): Boolean {
        return try {
            // Basic pattern check first for performance
            if (!Patterns.WEB_URL.matcher(urlString).matches()) {
                return false
            }

            // More thorough validation
            val url = java.net.URL(
                if (urlString.startsWith("http://") || urlString.startsWith("https://")) {
                    urlString
                } else {
                    "https://$urlString"
                }
            )

            // Additional checks for security and validity
            val host = url.host
            when {
                host.isNullOrBlank() -> false
                host.length > 253 -> false // DNS limit
                host.contains("..") -> false // Invalid host format
                host.startsWith(".") || host.endsWith(".") -> false
                // Add more security checks as needed
                else -> true
            }
        } catch (e: Exception) {
            Log.w("URLValidation", "Invalid URL: $urlString", e)
            false
        }
    }
}