package com.choonsik.security_sample.ui.sample_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.common.DataBindingAdapter
import com.choonsik.security_sample.databinding.FragmentSampleListBinding
import com.choonsik.security_sample.ui.model.UISampleModel
import com.choonsik.security_sample.ui.pin.PinFragment

class SampleListFragment : BaseFragment<SampleListViewModel, FragmentSampleListBinding>(
    R.layout.fragment_sample_list,
    SampleListViewModel::class
) {
    private val sampleListAdapter = SampleListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initObserve()
    }

    private fun initView() {
        sampleListAdapter.apply {
            submitList(viewModel.sampleList)
            itemClickListener = object : DataBindingAdapter.ItemClickListener<UISampleModel> {

                override fun onClickItem(item: UISampleModel) {
                    viewModel.onClickItem(item)
                }
            }
        }

        binding.rvSamples.apply {
            adapter = sampleListAdapter
        }
    }

    private fun initObserve() {
        viewModel.itemEvent.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let {

                val direction = if (it.position == 0) {
                    SampleListFragmentDirections.actionSimpleCrypt()
                } else if (it.position == 1) {
                    SampleListFragmentDirections.actionPin()
                } else {
                    SampleListFragmentDirections.actionSimpleCrypt()
                }

                binding.root.findNavController().navigate(direction)
            }
        })
    }
}