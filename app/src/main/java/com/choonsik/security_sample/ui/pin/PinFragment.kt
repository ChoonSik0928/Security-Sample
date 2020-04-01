package com.choonsik.security_sample.ui.pin

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentPinBinding

class PinFragment : BaseFragment<PinViewModel, FragmentPinBinding>(
    R.layout.fragment_pin, PinViewModel::class
){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}