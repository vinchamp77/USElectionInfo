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
    fun insertAll(asteroids: List<Election>)

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME}")
    fun getAll(): LiveData<List<Election>>

    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query

}