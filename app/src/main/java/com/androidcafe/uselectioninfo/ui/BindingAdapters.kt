package com.androidcafe.uselectioninfo.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.androidcafe.uselectioninfo.R
import com.androidcafe.uselectioninfo.data.VoterInfo

@BindingAdapter("electionInfoTitle")
fun setElectionInfoTitleText(view: TextView, voterInfo: VoterInfo?) {
    voterInfo?.run {
        view.text = view.resources.getString(R.string.election_info_text, stateName)
    }
}