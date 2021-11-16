package com.androidcafe.uselectioninfo.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidcafe.uselectioninfo.data.Election

@Dao
interface IElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(elections: List<Election>)

    // Note: no suspend is required if the returned value is LiveData
    @Query("SELECT * FROM ${DatabaseConstants.ELECTION_TABLE_NAME}")
    fun getAll(): LiveData<List<Election>>

    // Note: no suspend is required if the returned value is LiveData
    @Query("SELECT * FROM ${DatabaseConstants.ELECTION_TABLE_NAME} WHERE id = :id")
    fun get(id: Int) : LiveData<Election>

    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query

}