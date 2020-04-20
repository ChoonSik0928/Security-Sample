package com.choonsik.security_sample.ui.biometric_with_pin.validation

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentValidationBinding
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import kotlinx.android.synthetic.main.fragment_validation.*

class ValidationFragment : BaseFragment<ValidationViewModel, FragmentValidationBinding>(
    R.layout.fragment_validation,
    ValidationViewModel::class
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initValue()

        pin_keyboard.setKeyboardClickListener(object : KeyboardClickListener{
            override fun onKeyClick(key: PinKey) {
                viewModel.onKeyClick(key)
            }
        })
    }
}