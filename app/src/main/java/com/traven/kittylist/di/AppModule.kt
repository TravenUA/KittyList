package com.traven.kittylist.di

import com.traven.kittylist.domain.Repository
import com.traven.kittylist.Const
import com.traven.kittylist.domain.Iapi
import com.traven.kittylist.domain.Irepo
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
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun provideAPI(client: OkHttpClient): Iapi {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(Iapi::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(api: Iapi): Irepo {
        return Repository(api)
    }
}