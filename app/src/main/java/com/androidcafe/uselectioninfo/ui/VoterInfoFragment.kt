package com.androidcafe.uselectioninfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.androidcafe.uselectioninfo.databinding.VoterInfoFragmentBinding
import com.androidcafe.uselectioninfo.viewmodel.VoterInfoViewModel

class VoterInfoFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private val viewModel: VoterInfoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding = VoterInfoFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val arguments = VoterInfoFragmentArgs.fromBundle(requireArguments())
        viewModel.updateElection(arguments.election)

        //TODO: Add binding values

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
        */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks
        return binding.root
    }

    //TODO: Create method to load URL intents

}