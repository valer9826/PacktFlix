package com.packt.di

import android.content.Context
import com.packt.common.domain.AuthTokenProvider
import com.packt.data.AuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun authTokenProvider(@ApplicationContext context: Context): AuthTokenProvider {
        return AuthTokenProvider(context)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(authTokenProvider: AuthTokenProvider): AuthInterceptor {
        return AuthInterceptor(authTokenProvider)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://your.api.url/") // Replace with your actual base URL
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            authInterceptor
        ).build()
    }

}