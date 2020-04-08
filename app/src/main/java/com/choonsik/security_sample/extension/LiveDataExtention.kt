package com.choonsik.security_sample.extension

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Unit>.call() {
    value = Unit
}
