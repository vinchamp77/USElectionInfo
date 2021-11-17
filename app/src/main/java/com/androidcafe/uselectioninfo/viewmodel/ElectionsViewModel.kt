package com.androidcafe.uselectioninfo.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidcafe.uselectioninfo.R
import com.androidcafe.uselectioninfo.data.Division
import com.androidcafe.uselectioninfo.data.Election
import com.androidcafe.uselectioninfo.local.ActiveElectionDatabase
import com.androidcafe.uselectioninfo.local.SavedElectionDatabase
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import com.androidcafe.uselectioninfo.repository.ElectionsRepository
import kotlinx.coroutines.launch
import java.util.*

class ElectionsViewModel(app: Application) : BaseViewModel(app) {

    private val repository = ElectionsRepository(
        ActiveElectionDatabase.getInstance(app),
        SavedElectionDatabase.getInstance(app),
        CivicsApiInstance)

    val activeElections = repository.activeElections
    val savedElections = repository.savedElections

    private val mockData = false
    private val _mockElections = MutableLiveData<List<Election>>()
    val mockElections : LiveData<List<Election>>
        get() = _mockElections

    init {
        if(mockData) {
            mockElections()
        } else {
            refreshElections()
        }
    }

    private fun refreshElections() {
        viewModelScope.launch {
            try {
                repository.refreshElections()

            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBarInt.postValue(R.string.fail_no_network_msg)
            }
        }
    }

    private fun mockElections() {

        val mockElections = mutableListOf<Election>()

        var count = 1
        while (count <= 10) {

            val data = Election(
                count,
                "name:$count",
                Date(),
                Division("id", "US", "state")
            )

            mockElections.add(data)

            ++count
        }

        _mockElections.postValue(mockElections)
    }
}