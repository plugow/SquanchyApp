package com.plugow.squanchyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.squanchyapp.R
import com.plugow.squanchyapp.data.local.CharacterResult
import kotlinx.android.synthetic.main.character_item.*


class CharacterAdapter : BaseAdapter<CharacterResult>({CharacterResult()}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<CharacterResult> {
                return when(viewType){
                    TYPE_NORMAL -> CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false))
                    TYPE_LOADING -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false))
                    else -> CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<CharacterResult>, position: Int) {
        val episode = values[position]
        holder.bind(episode)
    }

    class CharacterViewHolder(containerView: View) : BaseViewHolder<CharacterResult>(containerView) {
        override fun bind(item: CharacterResult) {
            character_name.text = item.name
        }
    }

}