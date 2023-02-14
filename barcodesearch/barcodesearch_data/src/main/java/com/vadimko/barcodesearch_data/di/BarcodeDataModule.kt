package com.vadimko.barcodesearch_data.di

import com.vadimko.barcodesearch_data.remote.OpenFoodBarCodeApi
import com.vadimko.barcodesearch_data.repository.BarcodeSearchRepositoryImpl
import com.vadimko.barcodesearch_domain.repository.BarcodeSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BarcodeDataModule {

    @Provides
    @Singleton
    fun provideOpenFoodApi(client: OkHttpClient): OpenFoodBarCodeApi {
        return Retrofit.Builder()
            .baseUrl(OpenFoodBarCodeApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        api: OpenFoodBarCodeApi
    ): BarcodeSearchRepository {
        return BarcodeSearchRepositoryImpl(api)
    }
}
