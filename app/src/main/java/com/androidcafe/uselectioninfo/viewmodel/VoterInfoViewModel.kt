package com.androidcafe.uselectioninfo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidcafe.uselectioninfo.data.Election
import com.androidcafe.uselectioninfo.data.VoterInfo
import com.androidcafe.uselectioninfo.local.SavedElectionDatabase
import com.androidcafe.uselectioninfo.local.VoterInfoDatabase
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import com.androidcafe.uselectioninfo.repository.VoterInfoRepository
import kotlinx.coroutines.launch

class VoterInfoViewModel(app: Application): AndroidViewModel(app) {

    companion object {
        private const val DEFAULT_STATE = "la"
    }

    private val repository = VoterInfoRepository(
        VoterInfoDatabase.getInstance(app),
        SavedElectionDatabase.getInstance(app),
        CivicsApiInstance
    )

    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection : LiveData<Election>
        get() = _selectedElection

    val voterInfo = repository.voterInfo

    private val _isElectionSaved = MutableLiveData<Boolean?>()
    val isElectionSaved : LiveData<Boolean?>
        get() = _isElectionSaved

    private val mockData = true
    val mockVoterInfo = MutableLiveData<VoterInfo>()

    init {
        if(mockData) {
            val data = VoterInfo(
                2000,
                "State XYZ",
                "",
                "")
            mockVoterInfo.postValue(data)
        }

        _isElectionSaved.value = null
    }

    fun refresh(data: Election) {
        _selectedElection.value = data
        refreshIsElectionSaved(data)
        refreshVoterInfo(data)
    }

    private fun refreshIsElectionSaved(data: Election) {
        viewModelScope.launch {
            try {
                val savedElection = repository.getSavedElection(data.id)
                _isElectionSaved.postValue(savedElection != null)

            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    private fun refreshVoterInfo(data: Election) {
        viewModelScope.launch {
            try {
                val state = if(data.division.state.isEmpty()) DEFAULT_STATE else data.division.state
                val address = "${state},${data.division.country}"

                repository.refreshVoterInfo(address, data.id)
                loadVoterInfo(data.id)

            } catch (e: Exception) {
                e.printStackTrace()
                loadVoterInfo(data.id)
            }
        }
    }

    private suspend fun loadVoterInfo(id: Int) {
        try {
            repository.loadVoterInfo(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onFollowButtonClick() {
        viewModelScope.launch {
            _selectedElection.value?.let {
                if(isElectionSaved.value == true) {
                    repository.deleteSavedElection(it)
                } else {
                    repository.insertSavedElection(it)
                }
                refreshIsElectionSaved(it)
            }
        }
    }
}