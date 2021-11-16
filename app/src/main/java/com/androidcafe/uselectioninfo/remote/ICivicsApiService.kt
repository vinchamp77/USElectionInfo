package com.androidcafe.uselectioninfo.remote

import com.androidcafe.uselectioninfo.data.ElectionResponse
import com.androidcafe.uselectioninfo.data.RepresentativeResponse
import com.androidcafe.uselectioninfo.data.VoterInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */
interface ICivicsApiService {
    //https://developers.google.com/civic-information/docs/v2/elections/electionQuery
    //Example: https://www.googleapis.com/civicinfo/v2/elections?key=<API KEY>
    @GET("elections")
    suspend fun getElections(): ElectionResponse

    @GET("elections")
    suspend fun getElectionsJsonStr(): String

    //https://developers.google.com/civic-information/docs/v2/elections/voterInfoQuery
    // Example: https://www.googleapis.com/civicinfo/v2/voterinfo?key=<API KEY>&address=la,us&electionId=2000
    @GET("voterinfo")
    suspend fun getVoterInfo(
        @Query("address") address: String,
        @Query("electionId") electionId: Int
    ): VoterInfoResponse

    @GET("voterinfo")
    suspend fun getVoterInfoJsonStr(
        @Query("address") address: String,
        @Query("electionId") electionId: Int
    ): String

    @GET("representatives")
    suspend fun getRepresentatives(@Query("address") address: String): RepresentativeResponse

}