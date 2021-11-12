package com.androidcafe.uselectioninfo.data

import com.androidcafe.uselectioninfo.data.Election
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ElectionResponse(
        val kind: String,
        val elections: List<Election>
)