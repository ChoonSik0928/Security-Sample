package com.choonsik.security_sample.ui.biometric_with_pin

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.preference.PinPreference
import javax.inject.Inject

class BiometricWithPinViewModel @Inject constructor(
    private val pinPreference: PinPreference
) : ViewModel(){
    fun test(){
        pinPreference.useBiometric()
    }
}