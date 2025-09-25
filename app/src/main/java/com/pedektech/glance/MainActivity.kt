package com.pedektech.glance

import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme { ImageScreen() }
        }
    }

//https://youtu.be/No4hY1QwEnw



    interface HtmlService {
        @GET
        suspend fun fetchHtml(@Url url: String): Response<String>
    }

    data class LinkPreviewData(
        val title: String,
        val description: String,
        val imageUrl: String,
        val url: String,
        val message:String?=null
    )


    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36") // Use a recent browser UA
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("User-Agent", "WhatsApp/2.24.8.85")
                .header("Referer", "https://www.google.com/")
                .build()
            chain.proceed(request)
        }
        .followRedirects(true)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://example.com")
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val htmlService = retrofit.create(HtmlService::class.java)



    suspend fun parseHtmlForPreview(url: String): LinkPreviewData? {

        return try {
            // println("Attempting to fetch: $url")
            val response = htmlService.fetchHtml(url)
            //   println("Response received for $url - Successful: ${response.isSuccessful}, Code: ${response.code()}")

            if (response.isSuccessful && response.body() != null) {
                val htmlBody = response.body()!!

                val document = Jsoup.parse(htmlBody, url) // Provide base URI

                // Use more robust selectors, trying Twitter-specific ones first for x.com
                val title: String?
                val description: String?
                val image: String?

                if (url.contains("x.com", ignoreCase = true) || url.contains("twitter.com", ignoreCase = true)) {
                    title = document.selectFirst("meta[property=og:title]")?.attr("content")
                        ?: document.selectFirst("meta[name=twitter:title]")?.attr("content")
                                ?: document.title() // Fallback to page title
                    description = document.selectFirst("meta[property=og:description]")?.attr("content")
                        ?: document.selectFirst("meta[name=twitter:description]")?.attr("content")
                    image = document.selectFirst("meta[property=og:image]")?.attr("content")
                        ?: document.selectFirst("meta[name=twitter:image]")?.attr("content")
                } else {
                    // General selectors for other sites (adjust if needed)
                    title = document.selectFirst("meta[property=og:title]")?.attr("content")
                        ?: document.selectFirst("meta[name=title]")?.attr("content")
                                ?: document.title()
                    description = document.selectFirst("meta[property=og:description]")?.attr("content")
                        ?: document.selectFirst("meta[name=description]")?.attr("content")
                    image = document.selectFirst("meta[property=og:image]")?.attr("content")
                        ?: document.selectFirst("meta[name=image]")?.attr("content")
                }

                // Check if ANY meaningful data was extracted
                if (title.isNullOrBlank() && description.isNullOrBlank() && image.isNullOrBlank()) {
                    println("Failed to extract meaningful metadata from $url's received HTML.")

                    LinkPreviewData(
                        title = "",
                        description = "",
                        imageUrl = "",
                        url = url,
                        message = "Failed to extract meaningful metadata from $url's received HTML."
                    )
                }

                LinkPreviewData(
                    title = title?.takeIf { it.isNotBlank() } ?: "No title found", // Use takeIf for cleaner check
                    description = description?.takeIf { it.isNotBlank() } ?: "No description available",
                    imageUrl = image?.takeIf { it.isNotBlank() }?.let { resolveUrl(url, it) } ?: "", // Resolve relative URLs
                    url = url,
                    message = "success"
                )
            } else {
                val errorBody = response.errorBody()?.string() ?: "No error body"
                println("HTML Fetch Error for $url - Code: ${response.code()}, Message: ${response.message()}, Body: ${errorBody.take(500)}")
                LinkPreviewData(
                    title = "",
                    description = "",
                    imageUrl = "",
                    url = url,
                    message = "Error fetching HTML"
                )
            }
        } catch (e: Exception) {
            //println("Exception fetching or parsing $url: ${e.message}")
            e.printStackTrace()
            LinkPreviewData(
                title = "",
                description = "",
                imageUrl = "",
                url = url,
                message = "Exception fetching or parsing $url: ${e.message}"
            )
        }
    }

    // Helper function to resolve relative URLs to absolute ones
    fun resolveUrl(baseUrl: String, relativeUrl: String): String {
        return try {
            val baseUri = java.net.URI(baseUrl)
            baseUri.resolve(relativeUrl).toString()
        } catch (e: Exception) {
            relativeUrl // fallback to original if resolution fails
        }
    }

    @Composable
    fun ImageScreen() {
        var inputText by remember { mutableStateOf("") }
        var isSharePressed by remember { mutableStateOf(false) }
        var previewData by remember { mutableStateOf<LinkPreviewData?>(null) }
        var isLoading by remember { mutableStateOf(false) }
        var debouncedUrl by remember { mutableStateOf("") }

        LaunchedEffect(inputText) {
            // Basic URL validation (optional but recommended)
            if (inputText.trim().length > 10 && Patterns.WEB_URL.matcher(inputText.trim()).matches()) {
                isLoading = true
                delay(750L) // Wait 750ms after user stops typing
                debouncedUrl = inputText.trim() // Update the URL to fetch
            } else {
                // Clear preview if input is cleared or invalid
                previewData = null
                isLoading = false
                debouncedUrl = ""
            }
        }//https://www.youtube.com/watch?v=rLiIKmoZlhU

        LaunchedEffect(debouncedUrl) {
            if (debouncedUrl.isNotEmpty()) {
                //println("Debounced URL changed: $debouncedUrl. Fetching...")
                isLoading = true
                previewData = null // Clear previous preview
                // Ensure parseHtmlForPreview is called in a background thread
                withContext(Dispatchers.IO) {
                    previewData = parseHtmlForPreview(debouncedUrl)
                }
                isLoading = false
                println("Fetching complete for $debouncedUrl. Preview data: $previewData")
            }
        }

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
                onValueChange = { inputText = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            TechCrunchPreviewCard(
                modifier = Modifier.fillMaxWidth(),
                previewData
            )

            Spacer(modifier = Modifier.weight(1f))

            ShareActionButton(
                isPressed = isSharePressed,
                onPressedChange = { isSharePressed = it },
                onClick = { /* Handle share action */ }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

}
