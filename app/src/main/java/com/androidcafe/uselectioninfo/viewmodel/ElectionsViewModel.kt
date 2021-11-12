package com.androidcafe.uselectioninfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ElectionsViewModel : ViewModel() {
    private val mockData = true
    private val _mockElections = MutableLiveData<List<ElectionData>>()
    val mockElections : LiveData<List<ElectionData>>
        get() = _mockElections

    init {
        if(mockData) {
            mockElections()
        } else {
            //TODO:
        }
    }

    private fun mockElections() {

        val dataList = mutableListOf<ElectionData>()

        var count = 1
        while (count <= 10) {

            val data = ElectionData(
                count.toLong(),
                "name:$count",
                "XXXX-XX-XX")

            dataList.add(data)

            ++count
        }

        _mockElections.postValue(dataList)
    }
}