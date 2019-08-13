package com.jcardama.moviedatabase.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jcardama.moviedatabase.core.Config
import com.jcardama.moviedatabase.data.restful.MovieService
import com.jcardama.moviedatabase.util.factory.CoroutineCallAdapterFactory
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
        val original = chain.request()
        return@Interceptor chain.proceed(original.newBuilder()
                .url(original.url.newBuilder()
                        .addQueryParameter("api_key", Config.API_KEY)
                        .build()).build())
    })

    @Provides
    @Singleton
    fun providesRetrofit(
            gsonConverterFactory: GsonConverterFactory,
            okHttpClient: OkHttpClient,
            interceptors: ArrayList<Interceptor>
    ): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
        interceptors.forEach { interceptor ->
            clientBuilder.addInterceptor(interceptor)
        }
        return Retrofit.Builder().client(clientBuilder.build())
                .baseUrl(Config.HOST)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addNetworkInterceptor(interceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }
}
