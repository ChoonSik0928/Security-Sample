package com.choonsik.security_sample.ui.pin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.widget.pin.keyboard.PinKey
import javax.inject.Inject

class PinViewModel @Inject constructor() : ViewModel() {

    val inputKey = MutableLiveData<String>()
    private val _registrationKeys = arrayListOf<PinKey>()
    private val _inputKeys = arrayListOf<PinKey>()
    fun addPin() {

    }

    fun onKeyClick(key: PinKey) {
        when (key) {
            is PinKey.BackKey -> {

            }
            else -> {

            }
        }
    }

    private fun registrationKey(key: PinKey) {
        _registrationKeys.add(key)
    }

    companion object {
        private const val PIN_MAX_SIZE = 4
    }
}