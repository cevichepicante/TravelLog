package com.travellog.core.network.di

import com.travellog.core.network.TokenProvider
import com.travellog.core.network.api.AuthApi
import com.travellog.core.network.api.BadgeApi
import com.travellog.core.network.api.FriendApi
import com.travellog.core.network.api.MapApi
import com.travellog.core.network.api.MemoApi
import com.travellog.core.network.api.PhotoApi
import com.travellog.core.network.api.PlaceApi
import com.travellog.core.network.api.ScheduleApi
import com.travellog.core.network.api.TripApi
import com.travellog.core.network.api.UserApi
import com.travellog.core.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.travellog.app/v1/"

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider): AuthInterceptor =
        AuthInterceptor(tokenProvider)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF-8".toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi = retrofit.create(UserApi::class.java)

    @Provides
    @Singleton
    fun provideTripApi(retrofit: Retrofit): TripApi = retrofit.create(TripApi::class.java)

    @Provides
    @Singleton
    fun providePhotoApi(retrofit: Retrofit): PhotoApi = retrofit.create(PhotoApi::class.java)

    @Provides
    @Singleton
    fun providePlaceApi(retrofit: Retrofit): PlaceApi = retrofit.create(PlaceApi::class.java)

    @Provides
    @Singleton
    fun provideMemoApi(retrofit: Retrofit): MemoApi = retrofit.create(MemoApi::class.java)

    @Provides
    @Singleton
    fun provideMapApi(retrofit: Retrofit): MapApi = retrofit.create(MapApi::class.java)

    @Provides
    @Singleton
    fun provideBadgeApi(retrofit: Retrofit): BadgeApi = retrofit.create(BadgeApi::class.java)

    @Provides
    @Singleton
    fun provideScheduleApi(retrofit: Retrofit): ScheduleApi = retrofit.create(ScheduleApi::class.java)

    @Provides
    @Singleton
    fun provideFriendApi(retrofit: Retrofit): FriendApi = retrofit.create(FriendApi::class.java)
}
