package com.androidcafe.uselectioninfo.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.androidcafe.uselectioninfo.viewmodel.ElectionData

class ElectionListAdapter(private val itemClickListener: ElectionItemClickListener)
    : ListAdapter<ElectionData, ElectionViewHolder>(ElectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionViewHolder {
        return ElectionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ElectionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, itemClickListener)
    }
}