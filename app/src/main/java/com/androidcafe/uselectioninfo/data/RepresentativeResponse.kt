package com.androidcafe.uselectioninfo.data

import com.androidcafe.uselectioninfo.data.Office
import com.squareup.moshi.Json

data class RepresentativeResponse(
        val offices: List<Office>,
        val officials: List<Official>
)