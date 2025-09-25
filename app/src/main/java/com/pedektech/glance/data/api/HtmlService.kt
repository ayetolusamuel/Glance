package com.pedektech.glance.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Service for fetching raw HTML content from URLs
 */
interface HtmlService {
    @GET
    suspend fun fetchHtml(@Url url: String): Response<String>
}