package com.androidcafe.uselectioninfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidcafe.uselectioninfo.databinding.ElectionListItemBinding
import com.androidcafe.uselectioninfo.viewmodel.ElectionData


class ElectionViewHolder private constructor(private val binding: ElectionListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ElectionData, clickListener: ElectionItemClickListener) {
        binding.election = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun create(parent: ViewGroup) : ElectionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ElectionListItemBinding.inflate(layoutInflater, parent, false)
            return ElectionViewHolder(binding)
        }
    }
}