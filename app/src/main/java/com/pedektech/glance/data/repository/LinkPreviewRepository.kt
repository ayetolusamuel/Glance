package com.pedektech.glance.data.repository

import com.pedektech.glance.data.api.RetrofitClient
import com.pedektech.glance.data.model.LinkPreviewData
import com.pedektech.glance.util.UrlUtils
import org.jsoup.Jsoup
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Repository responsible for fetching and parsing link preview data
 */
class LinkPreviewRepository @Inject constructor(
    private val htmlService: RetrofitClient
) {

    suspend fun getLinkPreview(url: String): LinkPreviewData {
        return try {
            Timber.d("Fetching preview for URL: $url")
            val response = htmlService.htmlService.fetchHtml(url)

            if (response.isSuccessful && response.body() != null) {
                parseHtmlContent(response.body()!!, url)
            } else {
                Timber.e("HTTP Error: ${response.code()} - ${response.message()}")
                createErrorPreview(url, "HTTP Error: ${response.code()}")
            }
        } catch (e: UnknownHostException) {
            Timber.e("Network error for $url: ${e.message}")
            createErrorPreview(url, "Network unavailable")
        } catch (e: Exception) {
            Timber.e(e, "Error fetching preview for $url")
            createErrorPreview(url, "Failed to load preview")
        }
    }


    private fun parseHtmlContent(html: String, url: String): LinkPreviewData {
        val document = Jsoup.parse(html, url)

        val title = extractMetaContent(document,
            listOf("meta[property=og:title]", "meta[name=twitter:title]", "meta[name=title]")
        ) ?: document.title()

        val description = extractMetaContent(document,
            listOf("meta[property=og:description]", "meta[name=twitter:description]", "meta[name=description]")
        )?:""

        val image = extractMetaContent(document,
            listOf("meta[property=og:image]", "meta[name=twitter:image]", "meta[name=image]")
        )?.let { UrlUtils.resolveRelativeUrl(url, it) }

        return LinkPreviewData(
            title = title.takeIf { it.isNotBlank() } ?: "No title found",
            description = description?.takeIf { it.isNotBlank() } ?: "No description available",
            imageUrl = image?.takeIf { it.isNotBlank() } ?: "",
            url = url,
            message = "Success"
        )
    }

    private fun extractMetaContent(document: org.jsoup.nodes.Document, selectors: List<String>): String? {
        return selectors.firstNotNullOfOrNull { selector ->
            document.selectFirst(selector)?.attr("content")
        }
    }

    private fun createErrorPreview(url: String, errorMessage: String): LinkPreviewData {
        return LinkPreviewData.EMPTY.copy(
            url = url,
            message = errorMessage
        )
    }
}