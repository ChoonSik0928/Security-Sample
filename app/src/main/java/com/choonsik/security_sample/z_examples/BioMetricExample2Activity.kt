package com.choonsik.security_sample.z_examples

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.choonsik.security_sample.R
import kotlinx.android.synthetic.main.activity_bio_metric_exmaple.*
import java.util.concurrent.Executors
import javax.crypto.Cipher

class BioMetricExample2Activity : AppCompatActivity() {

    var encryptedData = byteArrayOf()
    var iv:ByteArray = byteArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio_metric_exmaple)
        btn_encrypt.setOnClickListener {
            encryptWithBioMetric()
        }

        btn_decrypt.setOnClickListener {
            decryptWithBioMetric()
        }
    }

    private fun encryptWithBioMetric() {
        val secretKey = KeystoreExample.createKey(TEST_KEY_ALIAS)
        val cipher = KeystoreExample.getEncryptCipher(secretKey)
        encryptWithBioMetricPrompt(cipher, PLAIN_TEXT.toByteArray(), {
            //TODO 실패
        }, {iv, resultByte->
            this.iv = iv
            this.encryptedData = resultByte
            val result = String(resultByte)
            runOnUiThread{
                tv_result.text = "Result : $result"
            }
        })
    }

    private fun encryptWithBioMetricPrompt(
        cipher: Cipher,
        data: ByteArray,
        failedAction: () -> Unit,
        successAction: (ByteArray, ByteArray) -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    result.cryptoObject?.cipher?.let { resultCipher ->
                        val iv = resultCipher.iv
                        val encryptedData = resultCipher.doFinal(data)
                        successAction(iv, encryptedData)
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    failedAction()
                }
            })

        val promptInfo = biometricPromptInfo()
        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

    private fun decryptWithBioMetric() {
        val secretKey = KeystoreExample.getKey(TEST_KEY_ALIAS)
        val cipher = KeystoreExample.getDecryptCipher(secretKey,iv)
        if (encryptedData.isEmpty()) return
        decryptWithBioMetricPrompt(cipher, encryptedData,  {
            //TODO 실패
        }, {
            val result = String(it)
            runOnUiThread{
                tv_result.text = "Result : $result"
            }
        })
    }

    private fun decryptWithBioMetricPrompt(
        cipher: Cipher,
        encryptedData: ByteArray,
        failedAction: () -> Unit,
        successAction: (ByteArray) -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    result.cryptoObject?.cipher?.let { resultCipher ->
                        val decryptedData = resultCipher.doFinal(encryptedData)
                        successAction(decryptedData)
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    failedAction()
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

    companion object {
        const val TEST_KEY_ALIAS = "TEST.ABC"
        const val TEST_VECTOR = "TEST_VECTOR"
        const val PLAIN_TEXT = "Choon-Sik"
    }
}