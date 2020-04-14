package com.choonsik.security_sample.preference

interface PinPreference {

    fun useBiometric()

    fun savePinInfo(info: String)

    fun getPinInfo() : String

    fun clearPin()

    fun isRegistered() : Boolean

    companion object {
        const val ENCRYPTED_PIN_NUMBER = "ENCRYPTED_PIN_NUMBER"
    }
}