package com.choonsik.security_sample.ui.sample_list

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.choonsik.security_sample.R
import com.choonsik.security_sample.common.DataBindingAdapter
import com.choonsik.security_sample.common.DataBindingViewHolder
import com.choonsik.security_sample.ui.model.UISampleModel

class SampleListAdapter : DataBindingAdapter<UISampleModel>(DiffCallback()) {

    override var itemClickListener: ItemClickListener<UISampleModel>? = null

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_sample
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<UISampleModel>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            Log.e("test","click")
            itemClickListener?.onClickItem(getItem(position))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<UISampleModel>() {
        override fun areItemsTheSame(oldItem: UISampleModel, newItem: UISampleModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: UISampleModel, newItem: UISampleModel): Boolean {
            return oldItem.name == newItem.name
        }
    }
}