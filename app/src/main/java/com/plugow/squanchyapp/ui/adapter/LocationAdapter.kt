package com.plugow.squanchyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plugow.squanchyapp.R
import com.plugow.squanchyapp.data.local.LocationResult
import kotlinx.android.synthetic.main.location_item.*


class LocationAdapter : BaseAdapter<LocationResult>({LocationResult()}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LocationResult> {
                return when(viewType){
                    TYPE_NORMAL -> LocationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false))
                    TYPE_LOADING -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false))
                    else -> LocationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<LocationResult>, position: Int) {
        val episode = values[position]
        holder.bind(episode)
    }

    class LocationViewHolder(containerView: View) : BaseViewHolder<LocationResult>(containerView) {
        override fun bind(item: LocationResult) {
            location_name.text = item.name.toString()
        }
    }

}