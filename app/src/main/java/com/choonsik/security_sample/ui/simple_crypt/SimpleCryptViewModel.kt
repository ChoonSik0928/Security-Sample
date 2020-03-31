package com.choonsik.security_sample.ui.simple_crypt

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.R
import com.choonsik.security_sample.util.crypt.CryptManager
import javax.inject.Inject

class SimpleCryptViewModel @Inject constructor() : ViewModel() {

    val editValue = MutableLiveData<String>()

    val encryptedValue = MutableLiveData<String>()
    val decryptedValue = MutableLiveData<String>()

    fun onButtonClick(view: View) {
        when (view.id) {
            R.id.btn_encrypt -> {
                val plainText = editValue.value ?: return
                encryptedValue.value = CryptManager.encryptPlainText(KEY_SIMPLE, plainText)
            }

            R.id.btn_decrypt -> {
                val encryptedText = encryptedValue.value ?: return
                decryptedValue.value = CryptManager.decryptPlainText(KEY_SIMPLE, encryptedText)
            }
        }
    }

    companion object {
        const val KEY_SIMPLE = "KEY_SIMPLE"
    }
}