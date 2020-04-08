package com.choonsik.security_sample.ui.biometric

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
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
        setObserver()
    }

    private fun setObserver() {
        with(viewModel.biometricObserver) {
            encrypted().observe(viewLifecycleOwner, Observer {
                viewModel.showEncryptedBiometric(this@BiometricSampleFragment)
            })

            decrypted().observe(viewLifecycleOwner, Observer {
//                viewModel.showEncryptedBiometric(this@BiometricSampleFragment)
            })
        }
    }
}