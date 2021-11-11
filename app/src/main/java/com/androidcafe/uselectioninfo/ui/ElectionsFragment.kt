package com.androidcafe.uselectioninfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androidcafe.uselectioninfo.databinding.ElectionsFragmentBinding
import com.androidcafe.uselectioninfo.viewmodel.ElectionsViewModel

class ElectionsFragment: Fragment() {

    private val viewModel: ElectionsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = ElectionsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        return binding.root

    }

    //TODO: Refresh adapters when fragment loads

}