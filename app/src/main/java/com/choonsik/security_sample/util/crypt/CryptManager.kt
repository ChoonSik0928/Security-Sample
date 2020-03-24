package com.choonsik.security_sample.util.crypt

class CryptManager {

    fun encryptPlainText(key: String, data: String) {
        val keyStore = KeyStoreWrapper.createKey(key)

    }
}