package com.androidcafe.uselectioninfo.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidcafe.uselectioninfo.local.DatabaseConstants
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class Election(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: Date,
    @Embedded(prefix = "division_") @Json(name = "ocdDivisionId") val division: Division
)