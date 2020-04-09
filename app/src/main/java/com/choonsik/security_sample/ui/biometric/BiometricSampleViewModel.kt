package com.choonsik.security_sample.ui.biometric

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choonsik.security_sample.R
import com.choonsik.security_sample.extension.call
import com.choonsik.security_sample.util.crypt.CryptManager
import kotlinx.android.synthetic.main.fragment_simple_crypt.view.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class BiometricSampleViewModel @Inject constructor() : ViewModel() {

    val plainText = "암호화 할 value = $PLAIN_TEXT"
    var encryptedValue = MutableLiveData<String>()
    var decryptedValue = MutableLiveData<String>()
    val startEncrypt = MutableLiveData<Unit>()
    val startDecrypt = MutableLiveData<Unit>()

    val biometricObserver = object : OnBiometricCallback {
        override fun encrypted() = startEncrypt

        override fun decrypted() = startDecrypt

    }

    fun startBiometric(view: View) {
        when (view.id) {
            R.id.btn_encrypt -> {
                startEncrypt.call()
            }
            R.id.btn_decrypt -> {
                startDecrypt.call()
            }
        }
    }

    fun showEncryptedBiometric(fragment: Fragment) {
        CryptManager.encryptWithBioMetric(
            fragment,
            KEY_BIOMETRIC,
            PLAIN_TEXT,
            { result ->
                viewModelScope.launch {
                    encryptedValue.value = result
                }

            },
            { errorCode, errorMessage ->
                Log.e("test", "error = ${errorCode} / ${errorMessage}")
            })
    }

    fun showDecryptedBiometric(fragment: Fragment) {
        if (encryptedValue.value.isNullOrEmpty()) return

        CryptManager.decryptWithBioMetric(
            fragment,
            KEY_BIOMETRIC,
            encryptedValue.value!!,
            { result ->
                viewModelScope.launch {
                    decryptedValue.value = "복호화 = $result"
                }
            },
            { errorCode, errorMessage ->
                Log.e("test", "error = ${errorCode} / ${errorMessage}")
            }
        )
    }

    interface OnBiometricCallback {
        fun encrypted(): LiveData<Unit>
        fun decrypted(): LiveData<Unit>
    }

    companion object {
        const val KEY_BIOMETRIC = "KEY_BIOMETRIC"
        const val PLAIN_TEXT = "PASSWORD-PLAIN-TEXT"
    }
}