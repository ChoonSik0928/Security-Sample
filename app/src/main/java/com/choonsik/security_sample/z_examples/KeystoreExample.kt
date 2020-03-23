package com.choonsik.security_sample.z_examples

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import java.security.Key
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object KeystoreExample {

    const val KEY_STORE_PROVIDER = "AndroidKeyStore" //fixed
    private const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS7Padding"

    val ks: KeyStore = KeyStore.getInstance(KEY_STORE_PROVIDER).apply {
        load(null)
    }

    //keyAlias = keyName
    fun getKey(keyAlias: String): Key {
      return ks.getKey(keyAlias, null)
    }

    /** 사용 가능한 알고리즘
     * KEY_ALGORITHM_RSA,
     * KEY_ALGORITHM_EC,
     * KEY_ALGORITHM_AES,
     * KEY_ALGORITHM_HMAC_SHA1,
     * KEY_ALGORITHM_HMAC_SHA224,
     * KEY_ALGORITHM_HMAC_SHA256,
     * KEY_ALGORITHM_HMAC_SHA384,
     * KEY_ALGORITHM_HMAC_SHA512
     *
     * 사용가능한 블럭모드
     * BLOCK_MODE_ECB,
     * BLOCK_MODE_CBC,
     * BLOCK_MODE_CTR,
     * BLOCK_MODE_GCM
     *
     * 사용가능한 패딩들
     *
     * ENCRYPTION_PADDING_NONE,
     * ENCRYPTION_PADDING_PKCS7,
     * ENCRYPTION_PADDING_RSA_PKCS1,
     * ENCRYPTION_PADDING_RSA_OAEP
     */
    fun createKey(keyStoreAlias: String): SecretKey {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE_PROVIDER)
        val keyGenParameterSpec =
            KeyGenParameterSpec.Builder(
                    keyStoreAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
//                .setUserAuthenticationRequired(true) // 디바이스에 사용자 인증이 됐을 때 사용 (ex 사용자의 디바이스 지문인증을 해제했을때 다시 사용 못하게 됨 , 일반적으로 지문인식 없이 사용할 경우 사용가능
                //setUserAuthenticationValidityDurationSeconds() // password 인증인 경우에는 특정 초 만 키를 이용할 수 있다는 것을 의미하며, 만약 “-1” 로 전달할 경우, 키에 접근 할때마다 지문 인증이 필요하다는 것
                //setInvalidatedByBiometricEnrollment() // 생체인증 정보가 변경될경우 키가 무효화 된다는 내용같은데 기본값은 TRUE임
                //setUserAuthenticationValidWhileOnBody(boolean remainsValid) //이는 장치가 더 이상 사람에게 있지 않음을 감지하면 키를 자동으로 잠그는 역할을 하게 됩니다.
                //setRandomizedEncryptionRequired //옵션을 사용하면 무작위로 랜덤화(랜덤 IV 사용))하게 되어, 동일한 데이터가 두 번째 암호화 될 경우 암호화되는 결과값은 달라질 수 잇게 됨
                .build()

        keyGenerator.init(keyGenParameterSpec)

        return keyGenerator.generateKey()
    }

    fun getEncryptCipher(key: Key): Cipher =
        Cipher.getInstance(CIPHER_TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, key)
        }

    fun getDecryptCipher(key: Key, iv:ByteArray): Cipher =
        Cipher.getInstance(CIPHER_TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE,key, IvParameterSpec(iv))
        }

}