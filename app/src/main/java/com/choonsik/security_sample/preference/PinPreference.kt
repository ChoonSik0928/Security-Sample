package com.choonsik.security_sample.preference

interface PinPreference {

    fun enrolBiometric(info: String)

    fun savePinInfo(info: String)

    fun getPinInfo() : String

    fun getBiometricInfo(): String

    fun clearPin()

    fun isRegistered() : Boolean

    fun isEnrolledBiometric() : Boolean

    companion object {
        const val ENCRYPTED_PIN_NUMBER = "ENCRYPTED_PIN_NUMBER"
        const val ENCRYPTED_PIN_NUMBER_WITH_BIOMETRIC = "ENCRYPTED_PIN_NUMBER_WITH_BIOMETRIC"
    }
}