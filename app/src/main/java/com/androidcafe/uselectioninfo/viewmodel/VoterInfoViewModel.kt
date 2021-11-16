package com.androidcafe.uselectioninfo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.androidcafe.uselectioninfo.data.Election
import com.androidcafe.uselectioninfo.data.VoterInfo
import com.androidcafe.uselectioninfo.local.ElectionDatabase
import com.androidcafe.uselectioninfo.local.VoterInfoDatabase
import com.androidcafe.uselectioninfo.remote.CivicsApiInstance
import com.androidcafe.uselectioninfo.repository.VoterInfoRepository
import kotlinx.coroutines.launch

//class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {
class VoterInfoViewModel(app: Application): AndroidViewModel(app) {

    companion object {
        private const val DEFAULT_STATE = "la"
    }

    private val repository = VoterInfoRepository(
        VoterInfoDatabase.getInstance(app),
        ElectionDatabase.getInstance(app),
        CivicsApiInstance
    )

    private val _election = MutableLiveData<Election>()
    val election : LiveData<Election>
        get() = _election

    val voterInfo = repository.voterInfo

    private val _isSaved = MutableLiveData<Boolean>()
    val isSaved : LiveData<Boolean>
        get() = _isSaved

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

        _isSaved.value = false
    }

    fun refresh(data: Election) {
        _election.value = data
        refreshVoterInfo(data)
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

    }

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}