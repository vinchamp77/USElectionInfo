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

        // active elections recycle view
        val activeElectionAdapter = ElectionListAdapter(ElectionItemClickListener { election ->
            navController.navigate(
                ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election)
            )
        })

        binding.upcomingElectionsRecyclerView.adapter = activeElectionAdapter
        viewModel.activeElections.observe(viewLifecycleOwner, { elections ->
            activeElectionAdapter.submitList(elections)
        })


        // saved elections recycle view
        val savedElectionAdapter = ElectionListAdapter(ElectionItemClickListener { election ->
            navController.navigate(
                ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election))
        })

        binding.savedElectionsRecyclerView.adapter = savedElectionAdapter
        viewModel.savedElections.observe(viewLifecycleOwner, { elections ->
            savedElectionAdapter.submitList(elections)
        })

        return binding.root
    }
}