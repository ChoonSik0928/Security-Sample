package com.choonsik.security_sample.ui.simple_crypt

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentSampleListBinding

class SimpleCryptFragment : BaseFragment<SimpleCryptViewModel, FragmentSampleListBinding>(
    R.layout.fragment_simple_crypt,
    SimpleCryptViewModel::class
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}