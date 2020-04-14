package com.choonsik.security_sample.preference

import android.content.SharedPreferences
import android.util.Log
import com.choonsik.security_sample.preference.PinPreference.Companion.ENCRYPTED_PIN_NUMBER
import javax.inject.Inject
import javax.inject.Singleton

class PinPreferenceImpl
@Inject constructor(private val pref: SharedPreferences) : PinPreference {

    override fun useBiometric() {

    }

    override fun savePinInfo(info: String) {
        pref.edit().putString(ENCRYPTED_PIN_NUMBER, info).apply()
    }

    override fun getPinInfo(): String {
        return pref.getString(ENCRYPTED_PIN_NUMBER, "")!!
    }

    override fun clearPin() {
        pref.edit().clear().apply()
    }

    override fun isRegistered(): Boolean {
        return !pref.getString(ENCRYPTED_PIN_NUMBER,"").isNullOrEmpty()
    }

}