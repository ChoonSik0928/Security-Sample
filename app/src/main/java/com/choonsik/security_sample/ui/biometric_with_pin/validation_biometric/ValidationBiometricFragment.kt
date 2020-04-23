package com.choonsik.security_sample.ui.biometric_with_pin.validation_biometric

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentValidationBiometricBinding

class ValidationBiometricFragment :
    BaseFragment<ValidationBiometricViewModel, FragmentValidationBiometricBinding>(
        R.layout.fragment_validation_biometric,
        ValidationBiometricViewModel::class
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.startBiometric(this)
    }
}