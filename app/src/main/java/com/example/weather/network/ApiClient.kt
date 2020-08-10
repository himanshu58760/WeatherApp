package com.example.nasaphotooftheday.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "https://api.openweathermap.org/"
    private var retrofit: Retrofit? = null
    val apiService: ApiInterface
        get() {
            if (retrofit == null) {
                val interceptor = HttpLoggingInterceptor()

                val client = OkHttpClient.Builder().addInterceptor(interceptor)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS).build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit?.create(ApiInterface::class.java)!!
        }
}