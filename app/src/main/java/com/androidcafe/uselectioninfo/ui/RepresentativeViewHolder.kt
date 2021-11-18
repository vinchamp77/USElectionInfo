package com.androidcafe.uselectioninfo.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidcafe.uselectioninfo.data.Representative
import com.androidcafe.uselectioninfo.databinding.RepresentativeListItemBinding


class RepresentativeViewHolder private constructor(private val binding: RepresentativeListItemBinding)
    : RecyclerView.ViewHolder(binding.root) {

    private var twitterUrl: String? = null
    private var facebookUrl: String? = null
    private var wwwUrl: String? = null

    companion object {
        fun create(parent: ViewGroup) : RepresentativeViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RepresentativeListItemBinding.inflate(layoutInflater, parent, false)
            return RepresentativeViewHolder(binding)
        }
    }

    fun bind(item: Representative) {
        binding.representative = item
        bindSocialLinks(item)
        binding.executePendingBindings()
    }

    private fun bindSocialLinks(item: Representative) {
        twitterUrl = getTwitterUrl(item)
        facebookUrl = getFacebookUrl(item)
        wwwUrl = getWwwUrl(item)

        if(twitterUrl == null) {
            binding.twitter.visibility = View.GONE
        } else {
            binding.twitter.setOnClickListener {
                startActivityUrlIntent(twitterUrl!!)
            }
        }
        if(facebookUrl == null) {
            binding.facebook.visibility = View.GONE
        } else {
            binding.facebook.setOnClickListener {
                startActivityUrlIntent(facebookUrl!!)
            }
        }

        if(wwwUrl == null) {
            binding.www.visibility = View.GONE
        } else {
            binding.www.setOnClickListener {
                startActivityUrlIntent(wwwUrl!!)
            }
        }
    }

    private fun getTwitterUrl(data: Representative):String? {
        val twitterChannels = data.official.channels?.filter{
            it.type == "Twitter"
        }

        val twitterChannel = twitterChannels?.firstOrNull()

        var twitterUrl:String? = null
        twitterChannel?.run {
            twitterUrl = "https://www.twitter.com/${twitterChannel.id}"
        }

        return twitterUrl
    }

    private fun getFacebookUrl(data: Representative):String? {
        val facebookChannels = data.official.channels?.filter{
            it.type == "Facebook"
        }

        val facebookChannel = facebookChannels?.firstOrNull()

        var facebookUrl:String? = null
        facebookChannel?.run {
            facebookUrl = "https://www.facebook.com/${facebookChannel.id}"
        }

        return facebookUrl
    }

    private fun getWwwUrl(data: Representative): String? {

        return data.official.urls?.firstOrNull()
    }

    private fun startActivityUrlIntent(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        itemView.context.startActivity(intent)
    }

}