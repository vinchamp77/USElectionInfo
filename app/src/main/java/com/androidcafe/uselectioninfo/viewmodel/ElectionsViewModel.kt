package com.androidcafe.uselectioninfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidcafe.uselectioninfo.repository.ElectionsRepository
import kotlinx.coroutines.launch

class ElectionsViewModel : ViewModel() {

    private val repository = ElectionsRepository()
    val elections = repository.elections

//    private val mockData = true
//    private val _elections = MutableLiveData<List<Election>>()
//    val elections : LiveData<List<Election>>
//        get() = _elections

    init {
//        if(mockData) {
//            mockElections()
//        } else {
//            //TODO:
//        }
        refreshElections()
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

//    private fun mockElections() {
//
//        val mockElections = mutableListOf<Election>()
//
//        var count = 1
//        while (count <= 10) {
//
//            val data = Election(
//                count,
//                "name:$count",
//                Date(),
//                Division("id", "US", "state")
//            )
//
//            mockElections.add(data)
//
//            ++count
//        }
//
//        _elections.postValue(mockElections)
//    }
}