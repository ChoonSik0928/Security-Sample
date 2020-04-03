package com.choonsik.security_sample.ui.pin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import javax.inject.Inject

class PinViewModel @Inject constructor() : ViewModel() {

    val registrationKey = MutableLiveData<String>()
    val inputKey = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    var isFinished = false
    private val _registrationKeys = arrayListOf<PinKey>()
    private val _inputKeys = arrayListOf<PinKey>()


    fun initValue() {
        displayText()
    }

    fun onKeyClick(key: PinKey) {
        if (isFinished) {
            return
        }
        when (key) {
            is PinKey.BackKey -> {
                if (_inputKeys.size in 1 until PIN_MAX_SIZE) {
                    _inputKeys.removeAt(_inputKeys.lastIndex)
                    displayText()
                }
            }
            else -> {
                // +1 마지막 등록된
                if (!isRegistration() && _inputKeys.size + 1 == PIN_MAX_SIZE) {
                    _inputKeys.add(key)
                    registrationKey()
                    displayText()
                } else if (isRegistration() && _inputKeys.size + 1 == PIN_MAX_SIZE) {
                    _inputKeys.add(key)
                    val isEquals = isEqualsKey()
                    if (isEquals) {
                        description.value = "핀번호가 일치합니다"
                        isFinished = true
                    } else {
                        description.value = "핀번호가 일치하지 않습니다"
                        _inputKeys.clear()
                    }
                    displayText()
                } else {
                    _inputKeys.add(key)
                    displayText()
                }
            }
        }
    }

    private fun isEqualsKey(): Boolean {
        var isEquals = true
        _inputKeys.forEachIndexed { index, pinKey ->
            if (!PinKey.getString(_registrationKeys[index])
                    .equals(PinKey.getString(pinKey))
            ) {
                isEquals = false
                return@forEachIndexed
            }
        }
        return isEquals
    }

    private fun displayText() {
        var displayText = "[ "
        for (index in 0 until PIN_MAX_SIZE) {
            if (_inputKeys.getOrNull(index) != null) {
                displayText += " ${PinKey.getString(_inputKeys[index])} "
            } else {
                displayText += " - "
            }
        }
        displayText += " ]"
        inputKey.value = displayText
    }

    private fun registrationDisplayText() {
        var displayText = "등록된 키 번호 = [ "
        _registrationKeys.forEach {
            displayText += " ${PinKey.getString(it)} "
        }
        displayText += " ]"

        registrationKey.value = displayText
    }

    private fun isRegistration(): Boolean {
        return _registrationKeys.isNotEmpty()
    }

    private fun registrationKey() {
        _registrationKeys.addAll(_inputKeys)
        _inputKeys.clear()
        registrationDisplayText()
    }

    companion object {
        private const val PIN_MAX_SIZE = 4
    }
}