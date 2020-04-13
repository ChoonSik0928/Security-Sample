package com.choonsik.security_sample.di.module

import android.content.Context
import android.content.SharedPreferences
import com.choonsik.security_sample.SecuritySampleApplication
import com.choonsik.security_sample.preference.PinPreference
import com.choonsik.security_sample.preference.PinPreferenceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class PreferenceModule {
    companion object{
        const val PIN_SETTING = "PIN_SETTING"
    }

    @Provides
    @Named(PIN_SETTING)
    fun providePinSharedPreference(application: SecuritySampleApplication) : SharedPreferences =
        application.applicationContext.getSharedPreferences("${application.applicationContext.packageName}_$PIN_SETTING", Context.MODE_PRIVATE)

    @Provides
    fun providePinPreference(@Named(PIN_SETTING) sharedPreferences: SharedPreferences): PinPreference {
        return PinPreferenceImpl(sharedPreferences)
    }

}