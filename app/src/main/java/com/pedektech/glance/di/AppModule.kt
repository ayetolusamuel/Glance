package com.pedektech.glance.di

import com.pedektech.glance.data.api.RetrofitClient
import com.pedektech.glance.data.repository.LinkPreviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitClient(): RetrofitClient {
        return RetrofitClient()
    }

    @Provides
    @Singleton
    fun provideLinkPreviewRepository(retrofitClient: RetrofitClient): LinkPreviewRepository {
        // The htmlService will be lazily initialized when first accessed
        return LinkPreviewRepository(retrofitClient)
    }
}