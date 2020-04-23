package com.choonsik.security_sample.preference

import android.content.SharedPreferences
import android.util.Log
import com.choonsik.security_sample.preference.PinPreference.Companion.ENCRYPTED_PIN_NUMBER
import com.choonsik.security_sample.preference.PinPreference.Companion.ENCRYPTED_PIN_NUMBER_WITH_BIOMETRIC
import javax.inject.Inject
import javax.inject.Singleton

class PinPreferenceImpl
@Inject constructor(private val pref: SharedPreferences) : PinPreference {

    override fun enrolBiometric(info: String) {
        Log.e("Test","info = ${info}")
        pref.edit().putString(ENCRYPTED_PIN_NUMBER_WITH_BIOMETRIC, info).apply()
    }

    override fun savePinInfo(info: String) {
        pref.edit().putString(ENCRYPTED_PIN_NUMBER, info).apply()
    }

    override fun getPinInfo(): String {
        return pref.getString(ENCRYPTED_PIN_NUMBER, "")!!
    }

    override fun getBiometricInfo(): String {
        return pref.getString(ENCRYPTED_PIN_NUMBER_WITH_BIOMETRIC,"")!!
    }

    override fun clearPin() {
        pref.edit().clear().apply()
    }

    override fun isRegistered(): Boolean {
        val value = pref.getString(ENCRYPTED_PIN_NUMBER, "")
        return !value.isNullOrEmpty()
    }

    override fun isEnrolledBiometric(): Boolean{
        val value = pref.getString(ENCRYPTED_PIN_NUMBER_WITH_BIOMETRIC, "")
        Log.e("test","value $value")
        return !value.isNullOrEmpty()
    }
}