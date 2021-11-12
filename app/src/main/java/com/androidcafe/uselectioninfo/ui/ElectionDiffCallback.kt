package com.androidcafe.uselectioninfo.ui

import androidx.recyclerview.widget.DiffUtil
import com.androidcafe.uselectioninfo.data.Election

class ElectionDiffCallback: DiffUtil.ItemCallback<Election>() {
    override fun areItemsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Election, newItem: Election): Boolean {
        return oldItem == newItem
    }
}
