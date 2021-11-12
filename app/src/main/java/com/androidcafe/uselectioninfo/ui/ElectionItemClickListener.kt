package com.androidcafe.uselectioninfo.ui

import com.androidcafe.uselectioninfo.data.Election

class ElectionItemClickListener(private val clickListener: (Election) -> Unit) {
    fun onClick(data: Election) = clickListener(data)
}