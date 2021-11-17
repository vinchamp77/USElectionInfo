package com.androidcafe.uselectioninfo.repository

import com.androidcafe.uselectioninfo.local.ActiveElectionDatabase
import com.androidcafe.uselectioninfo.local.SavedElectionDatabase
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(
    private val activeElectionDatabase: ActiveElectionDatabase,
    private val savedElectionDatabase: SavedElectionDatabase,
    private val api: CivicsApiInstance) {

    val activeElections = activeElectionDatabase.getAll()
    val savedElections = savedElectionDatabase.getAll()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionResponse = api.getElections()
            activeElectionDatabase.insertAll(electionResponse.elections)
        }
    }
}