package com.choonsik.security_sample.z_examples

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.choonsik.security_sample.R
import com.choonsik.security_sample.util.crypt.CryptManager
import kotlinx.android.synthetic.main.activity_bio_metric_exmaple.*
import java.util.concurrent.Executors
import javax.crypto.Cipher

class BioMetricExample2Activity : AppCompatActivity() {

    var encryptedData: String = ""
    var iv: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio_metric_exmaple)
        btn_encrypt.setOnClickListener {
            encryptWithBioMetric()
        }

        btn_decrypt.setOnClickListener {
            decryptWithBioMetric()
        }

        val test = CryptManager.encryptPlainText("PIN", "abc")
        Log.e("test","value = ${test}")
        Log.e("tets","data = decrytedValue ${CryptManager.decryptPlainText("PIN",test)}")
    }

    private fun encryptWithBioMetric() {
        val secretKey = KeystoreExample.createKey(TEST_KEY_ALIAS)
        val cipher = KeystoreExample.getEncryptCipher(secretKey)
        encryptWithBioMetricPrompt(cipher, PLAIN_TEXT.toByteArray(), {
            //TODO 실패
        }, { iv, result ->
            this.iv = iv
            this.encryptedData = result
            runOnUiThread {
                tv_result.text = "Result : $result"
            }
        })
    }

    private fun encryptWithBioMetricPrompt(
        cipher: Cipher,
        data: ByteArray,
        failedAction: () -> Unit,
        successAction: (String, String) -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    result.cryptoObject?.cipher?.let { resultCipher ->
                        val iv = resultCipher.iv
                        val encryptedData = resultCipher.doFinal(data)
                        successAction(
                            encodingWithBase64ToString(iv),
                            encodingWithBase64ToString(encryptedData)
                        )
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
        val cipher = KeystoreExample.getDecryptCipher(secretKey, decodingWithBase64ToByteArray(iv))
        if (encryptedData.isEmpty()) return
        decryptWithBioMetricPrompt(cipher, decodingWithBase64ToByteArray(encryptedData), {
            //TODO 실패
        }, {
            runOnUiThread {
                tv_result.text = "Result : $it"
            }
        })
    }

    private fun decryptWithBioMetricPrompt(
        cipher: Cipher,
        encryptedData: ByteArray,
        failedAction: () -> Unit,
        successAction: (String) -> Unit
    ) {
        val executor = Executors.newSingleThreadExecutor()
        val biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    result.cryptoObject?.cipher?.let { resultCipher ->
                        val decryptedData = resultCipher.doFinal(encryptedData)
                        successAction(String(decryptedData))
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

    private fun encodingWithBase64ToString(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun decodingWithBase64ToByteArray(encodingData: String): ByteArray {
        return Base64.decode(encodingData, Base64.DEFAULT)
    }

    companion object {
        const val TEST_KEY_ALIAS = "TEST.ABC"
        const val TEST_VECTOR = "TEST_VECTOR"
        const val PLAIN_TEXT = "Choon-Sik"
    }
}