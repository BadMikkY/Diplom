package com.example.diplom.di

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
            .baseUrl("http://localhost:3000/user")
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
}