package com.choonsik.security_sample.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

interface PinPreference {

    fun useBiometric()

    fun savePinInfo(info: String)

    fun getPinInfo()

    fun clearPin()
}