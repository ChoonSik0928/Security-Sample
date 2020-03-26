package com.choonsik.security_sample.ui.sample_list

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentSampleListBinding

class SampleListFragment : BaseFragment<SampleListViewModel, FragmentSampleListBinding>(
    R.layout.fragment_sample_list,
    SampleListViewModel::class
) {
    private val sampleListAdapter = SampleListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sampleListAdapter.submitList(viewModel.sampleList)
        binding.rvSamples.apply {
            adapter = sampleListAdapter
        }
    }
}