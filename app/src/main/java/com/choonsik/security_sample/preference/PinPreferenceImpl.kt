package com.choonsik.security_sample.preference

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

class PinPreferenceImpl
@Inject constructor(sharedPreferences: SharedPreferences) :PinPreference{
    override fun useBiometric() {
        Log.e("test","event")
    }

    override fun savePinInfo(info: String) {
    }

    override fun getPinInfo() {
    }

    override fun clearPin() {
    }

}