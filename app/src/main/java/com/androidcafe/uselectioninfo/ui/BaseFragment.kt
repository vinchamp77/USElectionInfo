package com.androidcafe.uselectioninfo.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.androidcafe.uselectioninfo.viewmodel.BaseViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
    protected abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showSnackBar.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.showSnackBarInt.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), getString(it), Snackbar.LENGTH_LONG).show()
        })
    }
}