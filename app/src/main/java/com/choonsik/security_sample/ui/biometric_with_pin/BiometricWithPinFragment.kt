package com.choonsik.security_sample.ui.biometric_with_pin

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentBiometricWithPinBinding

class BiometricWithPinFragment :
    BaseFragment<BiometricWithPinViewModel, FragmentBiometricWithPinBinding>(
        R.layout.fragment_biometric_with_pin, BiometricWithPinViewModel::class
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}