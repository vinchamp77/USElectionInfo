package com.androidcafe.uselectioninfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.androidcafe.uselectioninfo.databinding.ElectionsFragmentBinding
import com.androidcafe.uselectioninfo.viewmodel.ElectionsViewModel

class ElectionsFragment: Fragment() {

    private val navController by lazy { findNavController() }
    private val viewModel: ElectionsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = ElectionsFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // elections recycle view
        val adapter = ElectionListAdapter(ElectionItemClickListener {
            navController.navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment())
        })

        binding.upcomingElectionsRecyclerView.adapter = adapter
        viewModel.elections.observe(viewLifecycleOwner, { data ->
            adapter.submitList(data)
        })

        //TODO: Link elections to voter info

        return binding.root

    }

    //TODO: Refresh adapters when fragment loads

}