package com.androidcafe.uselectioninfo.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.androidcafe.uselectioninfo.data.Election

@Dao
interface IElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(elections: List<Election>)

    // Note: no suspend is required if the returned value is LiveData
    @Query("SELECT * FROM ${DatabaseConstants.ELECTION_TABLE_NAME}")
    fun getAll(): LiveData<List<Election>>

    @Query("SELECT * FROM ${DatabaseConstants.ELECTION_TABLE_NAME} WHERE id = :id")
    suspend fun get(id: Int) : Election?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(election: Election)

    @Delete
    suspend fun delete(election: Election)

    @Query("DELETE FROM ${DatabaseConstants.ELECTION_TABLE_NAME}")
    suspend fun clear()
}