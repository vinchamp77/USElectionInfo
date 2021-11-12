package com.androidcafe.uselectioninfo.ui

import androidx.recyclerview.widget.DiffUtil
import com.androidcafe.uselectioninfo.viewmodel.ElectionData

class ElectionDiffCallback: DiffUtil.ItemCallback<ElectionData>() {
    override fun areItemsTheSame(oldItem: ElectionData, newItem: ElectionData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ElectionData, newItem: ElectionData): Boolean {
        return oldItem == newItem
    }
}
