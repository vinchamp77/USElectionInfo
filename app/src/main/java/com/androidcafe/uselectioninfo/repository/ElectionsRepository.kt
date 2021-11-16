package com.androidcafe.uselectioninfo.repository

import androidx.lifecycle.LiveData
import com.androidcafe.uselectioninfo.data.Election
import com.androidcafe.uselectioninfo.local.ElectionDatabase
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(
    private val database: ElectionDatabase,
    private val api: CivicsApiInstance) {

    val elections = database.getAll()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionResponse = api.getElections()
            database.insertAll(electionResponse.elections)
        }
    }

    fun getElection(id: Int): LiveData<Election> = database.get(id)
}