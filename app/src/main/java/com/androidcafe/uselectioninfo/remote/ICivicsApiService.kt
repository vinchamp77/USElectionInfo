package com.androidcafe.uselectioninfo.remote

import com.androidcafe.uselectioninfo.data.ElectionResponse
import retrofit2.http.GET

/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */
interface ICivicsApiService {
    //https://developers.google.com/civic-information/docs/v2/elections/electionQuery
    @GET("elections")
    suspend fun getElections(): ElectionResponse

    @GET("elections")
    suspend fun getElectionsJsonString(): String

    //TODO: Add voterinfo API Call

    //TODO: Add representatives API Call
}