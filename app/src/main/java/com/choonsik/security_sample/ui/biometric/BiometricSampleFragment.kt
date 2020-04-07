package com.choonsik.security_sample.ui.biometric

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentBiometricSampleBinding

class BiometricSampleFragment
    : BaseFragment<BiometricSampleViewModel, FragmentBiometricSampleBinding>(
    R.layout.fragment_biometric_sample,
    BiometricSampleViewModel::class
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}