package com.androidcafe.uselectioninfo.remote

import com.androidcafe.uselectioninfo.data.Election
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object CivicsApiInstance {

    private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

    // TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(CivicsHttpClient.getClient())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ICivicsApiService by lazy {
        retrofit.create(ICivicsApiService::class.java)
    }

    suspend fun getElections(): List<Election> {
        val electionResponse = retrofitService.getElections()

        return electionResponse.elections
    }

    suspend fun getElectionsJsonString(): String {
        //breakpoint added here reached
        val outcome = retrofitService.getElectionsJsonString()
        //breakpoint added here never reach
        return outcome
    }
}