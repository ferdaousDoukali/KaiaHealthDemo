package com.kaiahealth.demo.utils

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientFactory(
    private val baseUrl: String
) : RestClientFactory {

    private val gson by lazy {
        Gson().newBuilder().create()
    }

    private val httpClient by lazy {
        OkHttpClient().newBuilder().build()
    }

    private val baseRetrofit by lazy {
        Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl).build()
    }

    override fun <T> makeApiService(apiService: Class<T>): T {
        return baseRetrofit.newBuilder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(apiService)
    }
}