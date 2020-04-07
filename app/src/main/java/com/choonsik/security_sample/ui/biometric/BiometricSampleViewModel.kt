package com.choonsik.security_sample.ui.biometric

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.util.crypt.CryptManager
import javax.inject.Inject

class BiometricSampleViewModel @Inject constructor() : ViewModel() {

    val plainText = MutableLiveData<String>()

    fun startBiometric(view: View){
//        CryptManager.decryptWithBioMetric()
    }
}