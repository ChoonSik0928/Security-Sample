package com.choonsik.security_sample.ui.biometric_with_pin.registration

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.extension.call
import com.choonsik.security_sample.preference.PinPreference
import com.choonsik.security_sample.ui.pin.PinViewModel
import com.choonsik.security_sample.util.crypt.CryptManager
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import javax.inject.Inject


class RegistrationViewModel @Inject constructor(
    private val pinPreference: PinPreference
) : ViewModel() {
    val description = MutableLiveData<String>()
    val inputKey = MutableLiveData<String>()
    val registrationComplete = MutableLiveData<Unit>()
    var encryptedValue = ""

    private val _registrationKeys = arrayListOf<PinKey>()
    private val _inputKeys = arrayListOf<PinKey>()

    fun initValue() {
        description.value = "핀코드를 입력해주세요."
        displayInputPinCode()
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
                // +1 마지막 등록된
                if (!isRegistration() && _inputKeys.size + 1 == PIN_MAX_SIZE) {
                    _inputKeys.add(key)
                    registrationKey()
                    displayInputPinCode()
                    description.value = "핀번호를 다시 입력해주세요"
                } else if (isRegistration() && _inputKeys.size + 1 == PIN_MAX_SIZE) {
                    _inputKeys.add(key)
                    val isEquals = isEqualsKey()
                    if (isEquals) {
                        description.value = "핀번호가 일치합니다"
                        pinPreference.savePinInfo(encryptedValue)
                        registrationComplete.call()
                    } else {
                        description.value = "핀번호가 일치하지 않습니다"
                        _inputKeys.clear()
                    }
                    displayInputPinCode()
                } else {
                    _inputKeys.add(key)
                    displayInputPinCode()
                }
            }
        }
    }

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

    private fun isRegistration(): Boolean {
        return _registrationKeys.isNotEmpty()
    }

    private fun registrationKey() {
        _registrationKeys.addAll(_inputKeys)
        _inputKeys.clear()
        encryptedValue = encryptedValue(_registrationKeys)
    }

    private fun isEqualsKey(): Boolean {
        return decryptedValue() == pinKeysToPlainText(_inputKeys)
    }

    private fun pinKeysToPlainText(keys: ArrayList<PinKey>): String{
        val values = arrayListOf<String>()
        keys.forEach {
            values.add(PinKey.getString(it))
        }
        return values.toString()
    }

    private fun encryptedValue(keys: ArrayList<PinKey>): String {
        return CryptManager.encryptPlainText(PinViewModel.KEY_PIN, pinKeysToPlainText(keys))
    }

    private fun decryptedValue(): String {
        return CryptManager.decryptPlainText(KEY_PIN, encryptedValue)
    }

    companion object {
        private const val PIN_MAX_SIZE = 4
        const val KEY_PIN = "KEY_PIN"
    }
}