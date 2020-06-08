package com.fury.locationupdater.core

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fury.locationupdater.BR
import com.fury.locationupdater.listener.ItemClickListener

abstract class BaseRecyclerViewAdapter<T>(diffItemCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseRecyclerViewAdapter.BaseViewHolder<T>>(diffItemCallback) {

    private var itemClick: ItemClickListener<T>? = null

    fun setItemClick(listener: ItemClickListener<T>?) {
        this.itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent?.context), viewType, parent, false)
        return BaseViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int)= holder.bind(getItem(position), itemClick)


    class BaseViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, listener: ItemClickListener<T>?) {
            binding.setVariable(BR.model, item)
            binding.executePendingBindings()
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition,item)
            }
        }
    }
}