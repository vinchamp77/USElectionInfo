package com.androidcafe.uselectioninfo.ui

import com.androidcafe.uselectioninfo.viewmodel.ElectionData

class ElectionItemClickListener(private val clickListener: (ElectionData) -> Unit) {
    fun onClick(data: ElectionData) = clickListener(data)
}