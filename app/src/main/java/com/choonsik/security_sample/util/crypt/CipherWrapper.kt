package com.choonsik.security_sample.util.crypt

import android.util.Base64
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

object CipherWrapper {

    private const val TRANSFORMATION_SYMMETRIC = "AES/CBC/PKCS7Padding"
    private const val IV_SEPARATOR = "]"

    /**
     * @param useInitializationVector encrypt 할 경우 iv 와의 구분을 위한 vector 추가
     */
    fun encrypt(
        data: String,
        key: Key?,
        useInitializationVector: Boolean = false,
        transformation: String = TRANSFORMATION_SYMMETRIC
    ): String {
        val cipher = getCipher(transformation).apply {
            init(Cipher.ENCRYPT_MODE, key)
        }

        var result = ""
        if (useInitializationVector) {
            val iv = cipher.iv
            val ivString = Base64.encodeToString(iv, Base64.DEFAULT).trim()
            result = ivString + IV_SEPARATOR
        }

        val bytes = cipher.doFinal(data.toByteArray())
        result += Base64.encodeToString(bytes, Base64.DEFAULT)

        return result
    }

    /**
     * @see encrypt
     * @param useInitializationVector 동일한 값 사용
     * @param transformation 동일한 값 사용
     */
    fun decrypt(
        data: String,
        key: Key?,
        useInitializationVector: Boolean = false,
        transformation: String = TRANSFORMATION_SYMMETRIC
    ): String {
        val cipher = getCipher(transformation).apply {
            init(Cipher.DECRYPT_MODE, key)
        }

        var encodedString: String

        if (useInitializationVector) {
            val split = data.split(IV_SEPARATOR.toRegex())
            if (split.size != 2) throw IllegalArgumentException("Passed data is incorrect. There was no IV specified with it.")

            val ivString = split[0].trim()
            encodedString = split[1].trim()
            val ivSpec = IvParameterSpec(Base64.decode(ivString, Base64.DEFAULT))
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
        } else {
            encodedString = data
            cipher.init(Cipher.DECRYPT_MODE, key)
        }

        val encryptedData = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)

        return String(decodedData)
    }

    private fun getCipher(transformation: String): Cipher {
        return Cipher.getInstance(transformation)
    }
}