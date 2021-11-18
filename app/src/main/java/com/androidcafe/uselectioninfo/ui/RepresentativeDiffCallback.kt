package com.androidcafe.uselectioninfo.ui

import androidx.recyclerview.widget.DiffUtil
import com.androidcafe.uselectioninfo.data.Representative


class RepresentativeDiffCallback: DiffUtil.ItemCallback<Representative>() {
    override fun areItemsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem.official.name == newItem.official.name
    }

    override fun areContentsTheSame(oldItem: Representative, newItem: Representative): Boolean {
        return oldItem == newItem
    }
}
