package com.choonsik.security_sample.ui.biometric_with_pin.registration

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentRegistrationBinding
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : BaseFragment<RegistrationViewModel, FragmentRegistrationBinding>
    (R.layout.fragment_registration, RegistrationViewModel::class) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initValue()

        pin_keyboard.setKeyboardClickListener(object : KeyboardClickListener {
            override fun onKeyClick(key: PinKey) {
                viewModel.onKeyClick(key)
            }
        })

        viewModel.registrationComplete.observe(viewLifecycleOwner, Observer {
            this@RegistrationFragment.findNavController().popBackStack()
        })
    }
}