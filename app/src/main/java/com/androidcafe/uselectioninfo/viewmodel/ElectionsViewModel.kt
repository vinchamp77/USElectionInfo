package com.androidcafe.uselectioninfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidcafe.uselectioninfo.data.Division
import com.androidcafe.uselectioninfo.data.Election
import java.util.*

class ElectionsViewModel : ViewModel() {
    private val mockData = true
    private val _elections = MutableLiveData<List<Election>>()
    val elections : LiveData<List<Election>>
        get() = _elections

    init {
        if(mockData) {
            mockElections()
        } else {
            //TODO:
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

        _elections.postValue(mockElections)
    }
}