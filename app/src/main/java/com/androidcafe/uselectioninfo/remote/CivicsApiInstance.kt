package com.androidcafe.uselectioninfo.remote

import com.androidcafe.uselectioninfo.data.Election
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object CivicsApiInstance {

    private const val API_KEY = ""
    private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

    private val moshi = Moshi.Builder()
        .add(ElectionJsonAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(buildOkHttpClient())
        .baseUrl(BASE_URL)
        .build()

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val url = original
                .url()
                .newBuilder()
                .addQueryParameter("key", API_KEY)
                .build()
            val request = original
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }.build()
    }

    private val retrofitService: ICivicsApiService by lazy {
        retrofit.create(ICivicsApiService::class.java)
    }

    suspend fun getElections(): List<Election> {
        val electionResponse = retrofitService.getElections()
        return electionResponse.elections
    }
}