package com.example.diplom.di

import com.example.diplom.api.ServicesApi
import com.example.diplom.api.SpecialistApi
import com.example.diplom.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideUserApi(): UserApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.7:3000/")
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(UserApi::class.java)
    }


    @Provides
    @Singleton
    fun provideSpecialistApi(): SpecialistApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.7:3000/")
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(SpecialistApi::class.java)
    }


    @Provides
    @Singleton
    fun provideServiceApi(): ServicesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.100.7:3000/")
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ServicesApi::class.java)
    }
}