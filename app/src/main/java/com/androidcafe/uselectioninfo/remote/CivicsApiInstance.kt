package com.androidcafe.uselectioninfo.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CivicsApiInstance {

    private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

    // TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(CivicsHttpClient.getClient())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ICivicsApiService by lazy {
        retrofit.create(ICivicsApiService::class.java)
    }

//    private val retrofitService : IAsteroidApiService by lazy {
//        retrofit.create(IAsteroidApiService::class.java)
//    }

//    suspend fun getAsteroids() : List<Asteroid> {
//        val responseStr = retrofitService.getAsteroids("","", NetworkConstants.API_KEY)
//        val responseJsonObject = JSONObject(responseStr)
//
//        return parseAsteroidsJsonResult(responseJsonObject)
//    }
//
//    suspend fun getPictureOfDay() = retrofitService.getPictureOfDay(NetworkConstants.API_KEY)
}