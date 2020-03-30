package com.choonsik.security_sample.ui.simple_crypt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.choonsik.security_sample.base.BaseViewModel
import javax.inject.Inject

class SimpleCryptViewModel @Inject constructor() : ViewModel() {
    val editValue = MutableLiveData<String>()


}