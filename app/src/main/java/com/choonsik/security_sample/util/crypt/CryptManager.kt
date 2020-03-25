package com.choonsik.security_sample.util.crypt

import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors

object CryptManager {

    fun encryptPlainText(keyAlias: String, data: String): String {
        val keyStore = KeyStoreWrapper.createKey(keyAlias)
        return CipherWrapper.encrypt(data, keyStore, useInitializationVector = true)
    }

    fun decryptPlainText(keyAlias: String, data: String): String {
        val key = KeyStoreWrapper.getKey(keyAlias)
        return CipherWrapper.decrypt(data, key, useInitializationVector = true)
    }

    private fun encryptWithBioMetric(
        fragmentActivity: FragmentActivity,
        keyAlias: String,
        data: String,
        useInitializationVector: Boolean = true
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val keyStore = KeyStoreWrapper.createKey(keyAlias)
        val cipher = CipherWrapper.getEncryptCipher(keyStore)

        val biometricPrompt =
            BiometricPrompt(
                fragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val cipher = result.cryptoObject?.cipher!!
                        //TODO 인터페이스 추가
                        val encryptedData =
                            CipherWrapper.encrypt(data, cipher, useInitializationVector)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        //TODO 인터페이스 추가
                    }
                })

        val promptInfo = biometricPromptInfo()
        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

    fun decryptWithBioMetric(
        fragmentActivity: FragmentActivity,
        keyAlias: String,
        data: String,
        useInitializationVector: Boolean = true
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val key = KeyStoreWrapper.getKey(keyAlias)
        var cipher = CipherWrapper.getDecryptCipher(key)
        if (useInitializationVector) {
            val iv = CipherWrapper.getIVSpec(data)
            cipher = CipherWrapper.getDecryptCipher(key, iv)
        }
        val biometricPrompt =
            BiometricPrompt(
                fragmentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val cipher = result.cryptoObject?.cipher!!
                        //TODO 인터페이스 추가
                        val decryptData =
                            CipherWrapper.decrypt(cipher, data, useInitializationVector)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                    }
                })

        val promptInfo = biometricPromptInfo()
        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

    private fun biometricPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setSubtitle("Subtitle")
            .setDescription("Description")
            .setNegativeButtonText("Cancel")
            .build()
    }
}