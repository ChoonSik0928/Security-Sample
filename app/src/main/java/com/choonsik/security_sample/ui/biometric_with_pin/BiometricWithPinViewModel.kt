package com.choonsik.security_sample.ui.biometric_with_pin

import android.util.Log
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.R
import com.choonsik.security_sample.extension.call
import com.choonsik.security_sample.preference.PinPreference
import javax.inject.Inject

class BiometricWithPinViewModel @Inject constructor(
    private val pinPreference: PinPreference
) : ViewModel() {

    val pinStatusMessage = MutableLiveData<String>()
    private val _pinStatus = MutableLiveData<Int>()

    private val actionValidation = MutableLiveData<Unit>()
    private var actionRegistration = MutableLiveData<Unit>()

    val onClickNav = object : OnClickNav {
        override fun actionRegistration() = actionRegistration
        override fun actionValidation() = actionValidation
    }

    fun checkStatus() {
        if (pinPreference.isRegistered()) {
            pinStatusMessage.value = "등록된 PIN 확인"
            _pinStatus.value = 1
        } else {
            pinStatusMessage.value = "PIN 등록하기"
            _pinStatus.value = 0
        }
    }

    fun onClickButton(view: View) {
        when (view.id) {
            R.id.btn_registration -> {
                actionRegistration.call()
            }

            R.id.btn_validation ->{
                actionValidation.call()
            }
        }
    }

    fun isShowRegistration(): Int {
        return when (_pinStatus.value) {
            0 -> View.VISIBLE
            else -> View.GONE
        }
    }

    fun isShowValidation(): Int{
        return when (_pinStatus.value) {
            1 -> View.VISIBLE
            else -> View.GONE
        }
    }



    interface OnClickNav {
        fun actionRegistration(): LiveData<Unit>
        fun actionValidation(): LiveData<Unit>
    }
}