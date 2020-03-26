package com.choonsik.security_sample.ui.sample_list

import androidx.recyclerview.widget.DiffUtil
import com.choonsik.security_sample.R
import com.choonsik.security_sample.common.DataBindingAdapter
import com.choonsik.security_sample.ui.model.UISampleModel

class SampleListAdapter : DataBindingAdapter<UISampleModel>(DiffCallback()) {

    override var itemClickListener: ItemClickListener?
        get() = null
        set(value) {}

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_sample
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