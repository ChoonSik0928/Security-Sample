package com.choonsik.security_sample.z_examples

import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.KeyStore
import java.util.*
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class BioMetricSampleActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private var executor = Executor { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        executor = ContextCompat.getMainExecutor(this)
//        canAuthenticate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            showBiometricPrompt()
        }
    }

    private fun canAuthenticate() {
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                Log.d(TAG, "App can authenticate using biometrics.")
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Log.e(TAG, "No biometric features available on this device.")
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Log.e(TAG, "Biometric features are currently unavailable.")
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Log.e(TAG, "등록된 생체 인증 정보가 없을 경우")
            else -> {

            }
        }
    }

    //bottomSheet 처럼 custom dialog 화면 제공 BiometricFragment
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showBiometricPrompt() {
//
//        generateSecretKey(
//            KeyGenParameterSpec.Builder(
//                    KEY_NAME,
//                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
//                )
//                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
//                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
//                .setUserAuthenticationRequired(true)
//                // Invalidate the keys if the user has registered a new biometric
//                // credential, such as a new fingerprint. Can call this method only
//                // on Android 7.0 (API level 24) or higher. The variable
//                // "invalidatedByBiometricEnrollment" is true by default.
//                .setInvalidatedByBiometricEnrollment(true)
//                .build()
//        )
        generateSecretKey(
            KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setUserAuthenticationRequired(true)
                .setUserAuthenticationValidityDurationSeconds(10000)
                .build()
        )
//        val cipher = getCipher()
//        val secretKey = getSecretKey()
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey)


        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .setConfirmationRequired(false)
            .setNegativeButtonText("Cancel")
//            .setDeviceCredentialAllowed(tr  ue)
            .build()

        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    Log.e("test","Error")
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                            applicationContext,
                            "Authentication error: $errString", Toast.LENGTH_SHORT
                        )
                        .show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    Log.e("test", "onAuthenticationSucceeded")
                    super.onAuthenticationSucceeded(result)
//                    val authenticatedCryptoObject: BiometricPrompt.CryptoObject? =
//                        result.cryptoObject
//                    // User has verified the signature, cipher, or message
//                    // authentication code (MAC) associated with the crypto object,
//                    // so you can use it in your app's crypto-driven workflows.

                    Log.e("test", "1")
                    val test = result.cryptoObject
                    if(test == null){
                        Log.e("test"," result.cryptoObject = null")
                    }else{
                        val test1 = test.cipher
                        if(test1 == null){
                            Log.e("test"," cipher = null")
                        }else{
//                            val encryptedInfo: ByteArray = test1.doFinal(PASSWORD.toByteArray(Charset.defaultCharset()))
//                            Log.e(
//                                "MY_APP_TAG", "Encrypted information: " +
//                                        Arrays.toString(encryptedInfo)
//                            )
                        }
                    }

                    Log.e("test", "2")
                }

                override fun onAuthenticationFailed() {
                    Log.e("test", "onAuthenticationFailed")
                    super.onAuthenticationFailed()

                }

            })

        val cipher = getCipher()
        val secretKey = getSecretKey()
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
//            val encryptedInfo: ByteArray = cipher.doFinal(
//                PASSWORD.toByteArray(Charset.defaultCharset())
//            )
//            Log.e(
//                "MY_APP_TAG", "Encrypted information: " +
//                        Arrays.toString(encryptedInfo)
//            )

            biometricPrompt.authenticate(
                promptInfo,
                BiometricPrompt.CryptoObject(cipher)
            )
        } catch (e: InvalidKeyException) {
            Log.e("MY_APP_TAG", "Key is invalid.")
        } catch (e: UserNotAuthenticatedException) {
            Log.d("MY_APP_TAG", "The key's validity timed out.")
            biometricPrompt.authenticate(promptInfo)
        }
        // Displays the "log in" prompt.
//        biometricPrompt.authenticate(promptInfo)


    }

    private fun encryptSecretInformation() {
        // Exceptions are unhandled for getCipher() and getSecretKey().

    }

    private fun generateSecretKey(keyGenParameterSpec: KeyGenParameterSpec) {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore"
        )
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")

        // Before the keystore can be accessed, it must be loaded.
        keyStore.load(null)
        return keyStore.getKey(KEY_NAME, null) as SecretKey
    }

    private fun getCipher(): Cipher {
        return Cipher.getInstance(
            KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7
        )
    }

    companion object {
        const val KEY_NAME = "key"
        const val PASSWORD = "password"
    }

}