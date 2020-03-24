package com.choonsik.security_sample.util.crypt

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * <p> Android provides the following <code>KeyStore</code> types:
 * <table>
 *   <thead>
 *     <tr>
 *       <th>Algorithm</th>
 *       <th>Supported API Levels</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td>AndroidCAStore</td>
 *       <td>14+</td>
 *     </tr>
 *     <tr>
 *       <td>AndroidKeyStore</td>
 *       <td>18+</td>
 *     </tr>
 *     <tr class="deprecated">
 *       <td>BCPKCS12</td>
 *       <td>1-8</td>
 *     </tr>
 *     <tr>
 *       <td>BKS</td>
 *       <td>1+</td>
 *     </tr>
 *     <tr>
 *       <td>BouncyCastle</td>
 *       <td>1+</td>
 *     </tr>
 *     <tr>
 *       <td>PKCS12</td>
 *       <td>1+</td>
 *     </tr>
 *     <tr class="deprecated">
 *       <td>PKCS12-DEF</td>
 *       <td>1-8</td>
 *     </tr>
 *   </tbody>
 * </table>
 */
object KeyStoreWrapper {

    private const val KEY_STORE_TYPE_AND_PROVIDER = "AndroidKeyStore"

    //    Type	Description
//    jceks	The proprietary keystore implementation provided by the SunJCE provider.
//    jks	The proprietary keystore implementation provided by the SUN provider.
//    dks	A domain keystore is a collection of keystores presented as a single logical keystore. It is specified by configuration data whose syntax is described in DomainLoadStoreParameter.
//    pkcs11	A keystore backed by a PKCS #11 token.
//    pkcs12	The transfer syntax for personal identity information as defined in PKCS #12.
    //      spiMap.put(type, clazz); --> 코드를 따라가보면 service provider의 타입을 검색해서 찾는 듯 함
    private val keyStore = KeyStore.getInstance(KEY_STORE_TYPE_AND_PROVIDER).apply {
        load(null)
    }

    fun createKey(
        alias: String,
        userAuthenticationRequired: Boolean = false,
        invalidatedByBiometricEnrollment: Boolean = true,
        durationSeconds: Int = -1,
        userAuthenticationValidWhileOnBody: Boolean = true
    ) {
        // 패스워드는 키를 생성시 접근을 위한 password 인데 어떤 형태로 활용 방법을 고민
        keyStore.getKey(alias, null)
    }

    /**
     *  @param userAuthenticationRequired 사용자 인증 여부(ex 화면 잠금 기능 없이 사용 불가능)
     *  @param invalidatedByBiometricEnrollment 사용자 인증 정보가 추가되면 key 무효
     */
    private fun generateKey(
        alias: String,
        userAuthenticationRequired: Boolean = false,
        invalidatedByBiometricEnrollment: Boolean = true,
        durationSeconds: Int = -1,
        userAuthenticationValidWhileOnBody: Boolean = true
    ): SecretKey {
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE_TYPE_AND_PROVIDER)
        val builder = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setUserAuthenticationRequired(userAuthenticationRequired)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .setUserAuthenticationValidityDurationSeconds(durationSeconds)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setInvalidatedByBiometricEnrollment(invalidatedByBiometricEnrollment)
            builder.setUserAuthenticationValidWhileOnBody(userAuthenticationValidWhileOnBody)
        }
        keyGenerator.init(builder.build())
        return keyGenerator.generateKey()
    }

//    fun getAlias(): Enumeration<String>? {
//        return keyStore.aliases()
//    }
}