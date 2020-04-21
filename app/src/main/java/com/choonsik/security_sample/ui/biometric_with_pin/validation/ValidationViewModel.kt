package com.choonsik.security_sample.ui.biometric_with_pin.validation

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choonsik.security_sample.extension.call
import com.choonsik.security_sample.preference.PinPreference
import com.choonsik.security_sample.util.crypt.CryptManager
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import kotlinx.coroutines.launch
import javax.inject.Inject

class ValidationViewModel @Inject constructor(
    private val pinPreference: PinPreference
) : ViewModel() {
    val description = MutableLiveData<String>()
    val inputKey = MutableLiveData<String>()
    val successValidation = MutableLiveData<Unit>()

    private val _inputKeys = arrayListOf<PinKey>()

    fun initValue() {
        description.value = "등록한 핀코드를 입력해주세요."
    }

    fun onKeyClick(key: PinKey) {
        when (key) {
            is PinKey.BackKey -> {
                if (_inputKeys.size in 1 until PIN_MAX_SIZE) {
                    _inputKeys.removeAt(_inputKeys.lastIndex)
                    displayInputPinCode()
                }
            }
            else -> {
                _inputKeys.add(key)
                Log.e("test", "keys =${pinKeysToPlainText(_inputKeys)}")
                if (isLastInput()) {
                    if (isEqualsEncryptedValue(_inputKeys)) {
                        description.value = "핀코드가 일치합니다"
                        successValidation.call()
                    } else {
                        description.value = "% 등록된 핀코드가 아닙니다 %"
                        _inputKeys.clear()
                    }
                }
                displayInputPinCode()
            }
        }
    }

    fun setBiometric(fragment: Fragment){
        CryptManager.encryptWithBioMetric(
            fragment,
            KEY_PIN_BIOMETRIC,
            pinPreference.getPinInfo(),
            { result ->
                viewModelScope.launch {
                    pinPreference.enrolBiometric(result)
                }
            },
            { errorCode, errorMessage ->
                Log.e("test", "error = ${errorCode} / ${errorMessage}")
            })
    }

    private fun isLastInput() = _inputKeys.size == PIN_MAX_SIZE

    private fun displayInputPinCode() {
        var displayText = "[ "
        for (index in 0 until PIN_MAX_SIZE) {
            displayText += if (_inputKeys.getOrNull(index) != null) {
                " ${PinKey.getString(_inputKeys[index])} "
            } else {
                " - "
            }
        }
        displayText += " ]"
        inputKey.value = displayText
    }

    private fun isEqualsEncryptedValue(keys: ArrayList<PinKey>): Boolean {
        return pinKeysToPlainText(keys) == decryptedValue(pinPreference.getPinInfo())
    }

    private fun decryptedValue(encryptedValue: String): String {
        return CryptManager.decryptPlainText(KEY_PIN, encryptedValue)
    }

    private fun pinKeysToPlainText(keys: ArrayList<PinKey>): String {
        val values = arrayListOf<String>()
        keys.forEach {
            values.add(PinKey.getString(it))
        }
        return values.toString()
    }

    companion object {
        private const val PIN_MAX_SIZE = 4
        const val KEY_PIN = "KEY_PIN"
        const val KEY_PIN_BIOMETRIC = "KEY_PIN_BIOMETRIC"
    }

}