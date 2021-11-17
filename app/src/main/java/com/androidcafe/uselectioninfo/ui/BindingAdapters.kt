package com.androidcafe.uselectioninfo.ui

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.androidcafe.uselectioninfo.R
import com.androidcafe.uselectioninfo.data.VoterInfo

@BindingAdapter("electionInfoTitle")
fun bindElectionInfoTitleText(view: TextView, voterInfo: VoterInfo?) {
    voterInfo?.run {
        view.text = view.resources.getString(R.string.election_info_text, stateName)
    }
}

@BindingAdapter("followButtonText")
fun bindFollowButtonText(button: Button, isElectionSaved: Boolean?) {
    if(isElectionSaved != null) {
        if (isElectionSaved) {
            button.text = button.resources.getString(R.string.unfollow_election_text)
        } else {
            button.text = button.resources.getString(R.string.follow_election_text)
        }
    } else {
        button.text = ""
    }
}
