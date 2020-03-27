package com.choonsik.security_sample.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class DataBindingAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

    interface ItemClickListener<in T> {
        fun onClickItem(item: T)
    }

    abstract var itemClickListener: ItemClickListener<T>?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        return holder.bind(getItem(position))
    }
}