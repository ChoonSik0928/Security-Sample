package com.choonsik.security_sample.ui.pin

import android.os.Bundle
import android.view.View
import com.choonsik.security_sample.R
import com.choonsik.security_sample.base.BaseFragment
import com.choonsik.security_sample.databinding.FragmentPinBinding
import com.choonsik.security_sample.widget.pin.`interface`.KeyboardClickListener
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import kotlinx.android.synthetic.main.fragment_pin.*

class PinFragment : BaseFragment<PinViewModel, FragmentPinBinding>(
    R.layout.fragment_pin, PinViewModel::class
){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initValue()

        pin_keyboard.setKeyboardClickListener(object :KeyboardClickListener{
            override fun onKeyClick(key: PinKey) {
                viewModel.onKeyClick(key)
            }
        })
//        var result = arrayListOf<String>()
//        pin_keyboard.setKeyboardClickListener(object : KeyboardClickListener {
//            override fun onKeyClick(key: PinKey) {
//
//                when (key) {
//                    is PinKey.BackKey -> {
//                        if(result.isNotEmpty()){
//                            result.removeAt(result.lastIndex)
//                        }
//                    }
//
//                    is PinKey.Num -> {
//                        result.add(PinKey.getString(key))
//                    }
//                }
//
//                tv_result.text = result.toString()
//            }
//        })
    }
}