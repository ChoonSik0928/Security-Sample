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
        val cipher = getEncryptCipher(key, transformation)

        return encrypt(data, cipher, useInitializationVector)
    }

    fun encrypt(data: String, cipher: Cipher, useInitializationVector: Boolean): String {
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
        useInitializationVector: Boolean,
        transformation: String = TRANSFORMATION_SYMMETRIC
    ): String {
        var ivSpec: IvParameterSpec? = null

        if (useInitializationVector) {
            ivSpec = getIVSpec(data)
        }

        val cipher = getDecryptCipher(key, ivSpec, transformation)
        return decrypt(cipher, data, useInitializationVector)
    }

    fun decrypt(
        cipher: Cipher,
        data: String,
        useInitializationVector: Boolean
    ): String {
        var encodedString: String

        encodedString = if (useInitializationVector) {
            val split = data.split(IV_SEPARATOR.toRegex())
            split[1].trim()
        } else {
            data
        }

        val encryptedData = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedData = cipher.doFinal(encryptedData)

        return String(decodedData)
    }

    fun getDecryptCipher(
        key: Key?,
        ivSpec: IvParameterSpec? = null,
        transformation: String = TRANSFORMATION_SYMMETRIC
    ) = getCipher(transformation).apply {
        if (ivSpec == null) {
            init(Cipher.DECRYPT_MODE, key)
        } else {
            init(Cipher.DECRYPT_MODE, key, ivSpec)
        }
    }

    fun getIVSpec(data: String): IvParameterSpec {
        val split = data.split(IV_SEPARATOR.toRegex())
        val ivString = split[0].trim()
        return IvParameterSpec(Base64.decode(ivString, Base64.DEFAULT))
    }

    fun getEncryptCipher(key: Key?, transformation: String = TRANSFORMATION_SYMMETRIC): Cipher {
        return getCipher(transformation).apply {
            init(Cipher.ENCRYPT_MODE, key)
        }
    }

    private fun getCipher(transformation: String): Cipher {
        return Cipher.getInstance(transformation)
    }
}