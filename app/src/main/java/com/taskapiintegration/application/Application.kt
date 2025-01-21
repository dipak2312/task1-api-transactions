package com.taskapiintegration.application

import android.content.Context
import android.support.multidex.MultiDexApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Application : MultiDexApplication() {

    companion object {
        lateinit var context: Context
        lateinit var retrofit: Retrofit
        lateinit var executor: ExecutorService
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1)
        val interceptor = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        val okkHttpBuilder: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(600, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .build()
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl("https://api.prepstripe.com/").client(okkHttpBuilder)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

        retrofit = builder.build()
    }
}