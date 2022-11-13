package com.paperless.app.module

import com.paperless.app.service.PaperlessService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import com.paperless.app.BuildConfig
import com.paperless.app.repo.PaperlessRepository

@Module
@InstallIn(SingletonComponent::class)
class PaperlessModule {
    @Provides
    @Singleton
    fun provideDashboardApiService() : PaperlessService {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient.build())
            .build()
        return retrofit.create(PaperlessService::class.java)
    }

    @Provides
    @Singleton
    fun providePaperlessRepository(
        paperlessService: PaperlessService
    ) : PaperlessRepository = PaperlessRepository(paperlessService)
}