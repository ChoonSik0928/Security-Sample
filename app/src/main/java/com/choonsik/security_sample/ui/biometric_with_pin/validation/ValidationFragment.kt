package com.choonsik.security_sample.ui.biometric_with_pin.validation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

        pin_keyboard.setKeyboardClickListener(object : KeyboardClickListener {
            override fun onKeyClick(key: PinKey) {
                viewModel.onKeyClick(key)
            }
        })

        viewModel.successValidation.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(context!!)
                .setMessage("핀코드 대신 생체인증을 사용하시겠습니까?")
                .setPositiveButton("예") { dialog, _ ->
                    viewModel.setBiometric(this@ValidationFragment)
                    dialog.dismiss()
                }.setNegativeButton("아니오") { dialog, _ ->
                    this@ValidationFragment.findNavController().popBackStack()
                    dialog.dismiss()
                }
                .show()
        })
    }
}