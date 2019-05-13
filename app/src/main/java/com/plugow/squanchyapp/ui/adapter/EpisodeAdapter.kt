package com.plugow.squanchyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.squanchyapp.R
import com.plugow.squanchyapp.data.local.EpisodeResult
import kotlinx.android.synthetic.main.episode_item.*


class EpisodeAdapter : BaseAdapter<EpisodeResult>({EpisodeResult()}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<EpisodeResult> {
                return when(viewType){
                    TYPE_NORMAL -> EpisodeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent, false))
                    TYPE_LOADING -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false))
                    else -> EpisodeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.episode_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<EpisodeResult>, position: Int) {
        val episode = values[position]
        holder.bind(episode)
    }

    class EpisodeViewHolder(containerView: View) : BaseViewHolder<EpisodeResult>(containerView) {
        override fun bind(item: EpisodeResult) {
            episode_id.text = item.id.toString()
            episode_name.text = item.name
        }
    }

}