package com.choonsik.security_sample.ui.biometric_with_pin.registration

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>
    (R.layout.fragment_registration, RegistrationViewModel::class) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}