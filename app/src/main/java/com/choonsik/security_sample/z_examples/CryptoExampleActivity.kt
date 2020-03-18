package com.choonsik.security_sample.z_examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class CryptoExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val encryptedValue = AESExample.encrypt(PASSWORD, IV)
        val decryptedValue = AESExample.decrypt(encryptedValue, IV)

        Log.e("test","plainText = $PASSWORD / encryptedValue = $encryptedValue / decryptedValue = $decryptedValue")
    }

    companion object {
        //        const val KEY_SESSION_IV = "ivForSession"
        const val PASSWORD = "password"
        const val IV = "0000000LENGTH_16"
    }
}