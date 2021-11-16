package com.androidcafe.uselectioninfo.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidcafe.uselectioninfo.local.DatabaseConstants
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

import java.util.*

@Parcelize
@Entity(tableName = DatabaseConstants.ELECTION_TABLE_NAME)
data class Election(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: Date,
    @Embedded(prefix = "division_") @Json(name = "ocdDivisionId") val division: Division
) : Parcelable