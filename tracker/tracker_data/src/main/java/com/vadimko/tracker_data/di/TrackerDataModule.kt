package com.vadimko.tracker_data.remote.di

import android.app.Application
import androidx.room.Room
import com.vadimko.tracker_data.local.TrackerDao
import com.vadimko.tracker_data.local.TrackerDataBase
import com.vadimko.tracker_data.remote.OpenFoodApi
import com.vadimko.tracker_data.repository.TrackerRepositoryImpl
import com.vadimko.tracker_domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor( HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(client: OkHttpClient): OpenFoodApi{
        return Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrackerDateBase(app: Application): TrackerDataBase{
        return Room.databaseBuilder(
            app,
            TrackerDataBase::class.java,
            "tracker_db",
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(
        dataBase: TrackerDataBase,
        api: OpenFoodApi
    ): TrackerRepository{
        return TrackerRepositoryImpl(dataBase.dao, api)
    }

}