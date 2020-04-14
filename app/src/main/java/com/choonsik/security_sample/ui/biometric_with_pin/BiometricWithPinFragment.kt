package com.choonsik.security_sample.ui.biometric_with_pin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentBiometricWithPinBinding

class BiometricWithPinFragment :
    BaseFragment<BiometricWithPinViewModel, FragmentBiometricWithPinBinding>(
        R.layout.fragment_biometric_with_pin, BiometricWithPinViewModel::class
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkStatus()

        setObserver()
    }


    private fun setObserver() {

        with(viewModel.onClickNav) {
            actionRegistration().observe(viewLifecycleOwner, Observer {
                if (viewLifecycleOwner.lifecycle.currentState != Lifecycle.State.RESUMED) return@Observer
                val direction = BiometricWithPinFragmentDirections.actionRegistration()
                binding.root.findNavController().navigate(direction)
            })
        }
    }
}