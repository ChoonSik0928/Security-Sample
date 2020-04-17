package com.choonsik.security_sample.ui.biometric_with_pin.validation

import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.preference.PinPreference
import javax.inject.Inject

class ValidationViewModel @Inject constructor(
    private val pinPreference: PinPreference
): ViewModel()