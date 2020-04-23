package com.choonsik.security_sample.ui.biometric_with_pin.validation_biometric

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choonsik.security_sample.extension.call
import com.choonsik.security_sample.preference.PinPreference
import com.choonsik.security_sample.util.crypt.CryptManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class ValidationBiometricViewModel @Inject constructor(
    private val pinPreference: PinPreference
) : ViewModel() {

    val validationResult = MutableLiveData<String>()

    fun startBiometric(fragment: Fragment) {
        CryptManager.decryptWithBioMetric(
            fragment,
            KEY_PIN_BIOMETRIC,
            pinPreference.getBiometricInfo(),
            success = {
                if (it == pinPreference.getPinInfo()) {
                    viewModelScope.launch {
                        validationResult.value = "생체인증 성공"
                    }
                }
            },
            error = { errorCode, errorMessage ->
                viewModelScope.launch {
                    validationResult.value = "실패 원인 = $errorMessage"
                }
            }
        )
    }


    companion object {
        const val KEY_PIN_BIOMETRIC = "KEY_PIN_BIOMETRIC"
    }
}