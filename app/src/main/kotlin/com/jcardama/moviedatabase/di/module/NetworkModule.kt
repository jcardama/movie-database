package com.jcardama.moviedatabase.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.data.source.remote.restful.MovieService
import com.jcardama.moviedatabase.core.factory.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> = arrayListOf(Interceptor { chain ->
        return@Interceptor chain.proceed(chain.request().newBuilder()
                .url(chain.request().url.newBuilder()
                        .addQueryParameter("api_key", Config.API_KEY)
                        .build()).build())
    })

    @Provides
    @Singleton
    fun providesRetrofit(
            gsonConverterFactory: GsonConverterFactory,
            okHttpClient: OkHttpClient,
            interceptors: ArrayList<Interceptor>
    ): Retrofit = Retrofit.Builder().client(OkHttpClient.Builder().apply {
                interceptors.forEach { interceptor ->
                    addInterceptor(interceptor)
                }
            }.build())
            .baseUrl(Config.HOST)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS).apply {
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }.build()

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun providesMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)
}
