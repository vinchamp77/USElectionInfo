package com.androidcafe.uselectioninfo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.androidcafe.uselectioninfo.data.Election
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository {
    //TODO: Temp
    val elections = MutableLiveData<List<Election>>()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
//            val electionList = CivicsApiInstance.getElections()
//            elections.postValue(electionList)
            val tmp = CivicsApiInstance.getElectionsJsonString()
            Log.d("XXXX", tmp)
        }
    }
}