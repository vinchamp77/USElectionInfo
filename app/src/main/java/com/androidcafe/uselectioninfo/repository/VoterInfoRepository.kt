package com.androidcafe.uselectioninfo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.androidcafe.uselectioninfo.data.VoterInfo
import com.androidcafe.uselectioninfo.data.VoterInfoResponse
import com.androidcafe.uselectioninfo.local.ElectionDatabase
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VoterInfoRepository(
    private val database: ElectionDatabase,
    private val api: CivicsApiInstance) {

    val voterInfo = MutableLiveData<VoterInfo>()

    suspend fun refreshVoterInfo(address:String, id:Int) {
        withContext(Dispatchers.IO) {
            Log.d("vtsen", "test")
            val response = api.getVoterInfo(address, id)
            Log.d("vtsen", response.toString())

            val localVoterInfo = convertToVoterInfo(id, response)
            localVoterInfo?.run {
                voterInfo.postValue(this)
            }
        }
    }

    private fun convertToVoterInfo(id: Int, response: VoterInfoResponse) : VoterInfo? {

        var voterInfo: VoterInfo? = null
        val state = response.state

        if (state?.isNotEmpty() == true) {
            val votingLocatinUrl: String? =
                state[0].electionAdministrationBody.votingLocationFinderUrl?.run {
                this
            }

            val ballotInfoUrl: String? =
                state[0].electionAdministrationBody.ballotInfoUrl?.run {
                this
            }

            voterInfo = VoterInfo(
                id,
                state[0].name,
                votingLocatinUrl,
                ballotInfoUrl
            )
        }

        return voterInfo
    }
}