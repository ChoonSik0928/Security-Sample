package com.choonsik.security_sample.util

import android.content.Context
import androidx.biometric.BiometricManager

object BiometricUtil {
    fun isEnabledBiometric(context: Context): Boolean {
        return BiometricManager.from(context).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }
}