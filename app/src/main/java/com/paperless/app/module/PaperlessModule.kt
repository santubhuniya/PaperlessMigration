package com.paperless.app.module

import android.app.Application
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
import com.paperless.app.datamodel.UserProfile
import com.paperless.app.repo.PaperlessRepository
import com.paperless.app.repo.SharedPrefRepo
import com.paperless.app.repo.SharedPrefRepo.Companion.USER_PROFILE
import com.paperless.app.service.LoginService
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
class PaperlessModule {
    @Provides
    @Singleton
    fun provideDashboardApiService(sharedPrefRepo: SharedPrefRepo): PaperlessService {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).apply {
                addInterceptor(
                Interceptor{ chain ->
                    val builder = chain.request().newBuilder()
                    builder.header("Content-Type", "application/json")
                    builder.header("Authorization", "Bearer ${sharedPrefRepo.getSharedPref<UserProfile>(USER_PROFILE)?.jwt}")
                    return@Interceptor chain.proceed(builder.build())
                })
            }

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient.build())
            .build()
        return retrofit.create(PaperlessService::class.java)
    }

    @Provides
    @Singleton
    fun provideLoginApiService(): LoginService {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.LOGIN_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient.build())
            .build()
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun providePaperlessRepository(
        paperlessService: PaperlessService,
        loginService: LoginService
    ): PaperlessRepository = PaperlessRepository(paperlessService, loginService)

    @Provides
    @Singleton
    fun provideSharedPrefRepo(application: Application) : SharedPrefRepo {
        return  SharedPrefRepo(application)
    }

}