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
    val pinStatus = MutableLiveData<Int>()
    val actionCheck = MutableLiveData<Unit>()

    private var actionRegistration = MutableLiveData<Unit>()

    val onClickNav = object : OnClickNav {
        override fun actionRegistration() = actionRegistration
        override fun actionCheck() = actionCheck
    }

    fun checkStatus() {
        if (pinPreference.isRegistered()) {
            pinStatusMessage.value = "등록된 PIN 확인"
            pinStatus.value = 1
        } else {
            pinStatusMessage.value = "PIN 등록하기"
            pinStatus.value = 0
        }
    }

    fun onClickButton(view: View) {
        when (view.id) {
            R.id.btn_registration -> {
                Log.e("test", "click")
                actionRegistration.call()
            }
        }
    }

    @Bindable
    fun getPinStatus(): Int {
        return when (pinStatus.value) {
            0 -> View.VISIBLE
            else -> View.GONE
        }
    }

    interface OnClickNav {
        fun actionRegistration(): LiveData<Unit>
        fun actionCheck(): LiveData<Unit>
    }
}