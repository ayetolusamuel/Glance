package com.pedektech.glance.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.Interceptor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provides Retrofit client configuration and service instances
 */
@Singleton
class RetrofitClient @Inject constructor() {

    companion object {
        private const val TIMEOUT_SECONDS = 15L
        private const val BASE_URL = "https://example.com" // Required but not used with @Url
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(createUserAgentInterceptor())
            .followRedirects(true)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    val htmlService: HtmlService by lazy {
        retrofit.create(HtmlService::class.java)
    }

    private fun createUserAgentInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36") // Use a recent browser UA
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("User-Agent", "WhatsApp/2.24.8.85")
                .header("Referer", "https://www.google.com/")
                .build()
            chain.proceed(request)
        }
    }
}