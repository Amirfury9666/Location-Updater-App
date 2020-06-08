package com.fury.locationupdater.adapter

import androidx.recyclerview.widget.DiffUtil
import com.fury.locationupdater.R
import com.fury.locationupdater.core.BaseRecyclerViewAdapter
import com.fury.locationupdater.db.LocationEntity

class LocationAdapter : BaseRecyclerViewAdapter<LocationEntity>(DiffCallBack()) {
    override fun getItemViewType(position: Int): Int {
        return R.layout.location_item
    }

    private class DiffCallBack : DiffUtil.ItemCallback<LocationEntity>() {
        override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
            return oldItem.lat == oldItem.lat && oldItem.lang == newItem.lang && oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
            return oldItem.lat == oldItem.lat && oldItem.lang == newItem.lang && oldItem.time == newItem.time
        }
    }
}