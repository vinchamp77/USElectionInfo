package com.androidcafe.uselectioninfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androidcafe.uselectioninfo.databinding.LaunchFragmentBinding

class LaunchFragment : Fragment() {

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = LaunchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.representativesButton.setOnClickListener { navToRepresentatives() }
        binding.upcomingElectionsButton.setOnClickListener { navToElections() }

        return binding.root
    }

    private fun navToElections() {
        navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToElectionsFragment())
    }

    private fun navToRepresentatives() {
        navController.navigate(LaunchFragmentDirections.actionLaunchFragmentToRepresentativesFragment())
    }
}