package com.androidcafe.uselectioninfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidcafe.uselectioninfo.data.Division
import com.androidcafe.uselectioninfo.data.Election
import com.androidcafe.uselectioninfo.repository.ElectionsRepository
import kotlinx.coroutines.launch
import java.util.*

class ElectionsViewModel : ViewModel() {

    private val repository = ElectionsRepository()
    val elections = repository.elections

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