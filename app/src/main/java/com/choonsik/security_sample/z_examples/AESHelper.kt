package com.choonsik.security_sample.z_examples

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESHelper {

    private const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS5PADDING"
    private const val SECRET_KEY = "ANYTHING_16Bytes"

    fun encrypt(plainText: String, initVector: String): String {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(initVector.toByteArray()))

        return Base64.encodeToString(cipher.doFinal(plainText.toByteArray()), Base64.DEFAULT)
    }

    fun decrypt(base64CipherText: String, initVector: String): String {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        val secretKeySpec = SecretKeySpec(SECRET_KEY.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(initVector.toByteArray()))
        val planTextBytes = cipher.doFinal(Base64.decode(base64CipherText, Base64.DEFAULT))
        return String(planTextBytes)
    }
}