package com.androidcafe.uselectioninfo.repository

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
            val electionList = CivicsApiInstance.getElections()
            //TODO: Save to database
            elections.postValue(electionList)

        }
    }
}