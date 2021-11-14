package com.androidcafe.uselectioninfo.repository

import com.androidcafe.uselectioninfo.local.ElectionDatabase
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(private val database: ElectionDatabase) {
    val elections = database.getAll()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionList = CivicsApiInstance.getElections()
            database.insertAll(electionList)
        }
    }
}