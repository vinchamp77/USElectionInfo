package com.androidcafe.uselectioninfo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidcafe.uselectioninfo.local.DatabaseConstants

@Entity(tableName = DatabaseConstants.VOTER_INFO_TABLE_NAME)
data class VoterInfo(
    @PrimaryKey val id: Int,
    val stateName: String,
    val votingLocationUrl: String?,
    val ballotInformationUrl: String?)
